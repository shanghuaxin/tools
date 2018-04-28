package com.dingcheng.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dingcheng.common.dao.impl.BaseDaoImpl;
import com.dingcheng.user.dao.UserDao;
import com.dingcheng.user.model.User;

@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User login(String loginName, String pwd) {
		
		User user = null;
		Map<String, Object> map = new HashMap<>();
		map.put("loginName", loginName);
		map.put("password", pwd);
		List<User> list = super.findByMap(map);
		if(list!=null && list.size()>0) {
			user = list.get(0);
		}
		return user;
	}
}
