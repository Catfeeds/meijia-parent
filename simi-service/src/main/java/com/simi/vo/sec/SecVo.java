package com.simi.vo.sec;

import com.simi.po.model.sec.Sec;

public class SecVo extends Sec{
	private String imgUrlNew;

    private String username;

    private String password;


	public String getImgUrlNew() {
		return imgUrlNew;
	}

	public void setImgUrlNew(String imgUrlNew) {
		this.imgUrlNew = imgUrlNew;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
