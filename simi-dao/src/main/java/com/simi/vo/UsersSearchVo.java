package com.simi.vo;


public class UsersSearchVo {

	  private String mobile;
	  
	  private Long id;
	  
	  private String name;
	  
	  private Short userType;

	public String getMobile() {
		return mobile = mobile !=null ? mobile.trim():new String();
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name = name !=null ? name.trim():new String();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}
}
