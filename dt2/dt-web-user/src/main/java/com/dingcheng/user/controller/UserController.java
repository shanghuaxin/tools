package com.dingcheng.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dingcheng.activemq.util.QueueMessageSend;
import com.dingcheng.common.util.CookieUtil;
import com.dingcheng.common.util.SysConst;
import com.dingcheng.user.model.User;
import com.dingcheng.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "send-msg/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public String sendMsg(@PathVariable String msg) {
		QueueMessageSend.send(msg);
		
		return msg + " send success";
	}
	/**
	 * 展示用户
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model) {
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return "user/list";
	}
	
	/**
	 * 跳转到添加用户页面
	 * @param model
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "to-add")
	public String toAdd(Model model) {
		return "user/add";
	}
	
	/**
	 * 添加用户,完成后跳转到list
	 * @param model
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(Model model, String loginName,String password) {
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		List<User> userList = userService.findAll();
		user.setId(userList.get(userList.size()-1).getId()+1);
		userService.create(user);
		/*if(userService!=null){
			throw new RuntimeException("测试一下");
		}*/
		return "redirect:list";
	}
	
	@RequestMapping(value = "login-init", method = RequestMethod.GET)
	public String loginInit(HttpSession session, String msg) {
		System.out.println(msg);
		return "user/login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletResponse response, String loginName,String password) {
		
		User user = userService.login(loginName, password);
		if(user == null) {
			return "redirect:login-init";
		}
	    //设置cookie的key和value，key为用户id，value为token值
	    CookieUtil.newInstance().saveCookie(user.getToken(), SysConst.LOGIN_FLAG, (int)SysConst.REDIS_EXPIRE_TIME, response);
		return "redirect:list";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session, String loginName,String password) {
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		
		return "redirect:login-init";
	}

}
