package com.simi.vo;


public class UserSearchVo {

	  private String name;
	
	  private String mobile;
	  
	  private Long secId;
	  
	  private Short userType;
	  
	  private Short isApproval;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getSecId() {
		return secId;
	}

	public void setSecId(Long secId) {
		this.secId = secId;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public Short getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Short isApproval) {
		this.isApproval = isApproval;
	}
}
