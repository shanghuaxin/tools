package com.wangzhixuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingcheng.user.model.User;
import com.dingcheng.user.service.UserService;

@RestController
public class DubboTestController {

	@Autowired
	private UserService userService;
	
	/**
	 * 展示用户
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "user/list", method = RequestMethod.GET)
	public List<User> list(Model model) {
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return userList;
	}
}
