package com.dingcheng.user.dao;

import com.dingcheng.common.dao.BaseDao;
import com.dingcheng.user.model.User;

public interface UserDao extends BaseDao<User>{

	public User login(String loginName, String password);
}

