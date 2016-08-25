package com.xcloud.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginVo {

	
	private String username;

	private String password;
	
	private String loginType;

	public void setUsername(String username){
		this.username=username;
	}
	public void setPassword(String password){
		this.password=password;
	}

	public String getUsername(){
		return this.username;
	}
	public String getPassword(){
		return this.password;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
