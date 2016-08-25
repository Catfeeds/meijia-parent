package com.xcloud.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginVo {

	@NotEmpty(message="用户名不能为空")
	private String username;
	@NotEmpty(message="用户名和密码错误}")
	private String password;

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
}
