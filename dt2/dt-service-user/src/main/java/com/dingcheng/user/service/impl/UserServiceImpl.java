package com.dingcheng.user.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingcheng.common.dao.BaseDao;
import com.dingcheng.common.service.impl.BaseServiceImpl;
import com.dingcheng.common.util.JSONUtil;
import com.dingcheng.common.util.SysConst;
import com.dingcheng.redis.util.RedisUtils;
import com.dingcheng.user.dao.UserDao;
import com.dingcheng.user.model.User;
import com.dingcheng.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	@Autowired
	private UserDao userDaoImpl;
	
	@Override
	public BaseDao<User> getBaseDao() {
		return userDaoImpl;
	}

	@Override
	public User login(String loginName, String password) {
		// 1、判断用户名密码是否正确。
		User user = userDaoImpl.login(loginName, password);
		if(user == null) {
			return user;
		}
		// 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
		String token = UUID.randomUUID().toString();
        // 3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
		user.setToken(token);
        // 4、使用String类型保存Session信息。可以使用“前缀:token”为key
		// 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        RedisUtils.save(token, JSONUtil.objectToJson(user), SysConst.REDIS_EXPIRE_TIME);
		// 综上操作后，token-->user
		return user;
	}
}
