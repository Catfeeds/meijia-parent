package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.Xcompany;

public class XcompanyVo extends Xcompany {

	private String userName;
	
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
