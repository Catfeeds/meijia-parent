package com.simi.vo.xcloud;


public class CompanyAdminSearchVo{

	private Long companyId;
	
	private String companyName;
	
	private Long userId;
	
	private String userName;
	
	private String passMd5;
		
	private int isCreate;
	
	private Long startTime;
	
	private Long endTime;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassMd5() {
		return passMd5;
	}

	public void setPassMd5(String passMd5) {
		this.passMd5 = passMd5;
	}

	public int getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(int isCreate) {
		this.isCreate = isCreate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	

	
}
