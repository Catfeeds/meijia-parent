package com.xcloud.auth;

public class AccountAuth {

	private Long userId;
	private Long companyId;



	public AccountAuth(Long userId, Long companyId) {
		this.userId = userId;
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}



}
