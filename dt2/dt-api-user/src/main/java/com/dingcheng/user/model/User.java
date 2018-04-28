package com.dingcheng.user.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1648918864882213505L;
	private Integer id;
    private String loginName;
    private String password;
    private String token;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
