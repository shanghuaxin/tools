package com.dingcheng.user.service;

import com.dingcheng.common.service.BaseService;
import com.dingcheng.user.model.User;

public interface UserService extends BaseService<User>{
	public User login(String loginName, String password);
}
