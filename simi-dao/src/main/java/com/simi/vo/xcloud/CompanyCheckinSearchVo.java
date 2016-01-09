package com.simi.vo.xcloud;

public class CompanyCheckinSearchVo{
	
	private Long companyId;
		
	private Long userId;
	
	private Long staffId;
	
	private Short checkinFrom;
	
	private Short checkinType;
	
	private Short checkinStatus;
		
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

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Short getCheckinFrom() {
		return checkinFrom;
	}

	public void setCheckinFrom(Short checkinFrom) {
		this.checkinFrom = checkinFrom;
	}

	public Short getCheckinType() {
		return checkinType;
	}

	public void setCheckinType(Short checkinType) {
		this.checkinType = checkinType;
	}

	public Short getCheckinStatus() {
		return checkinStatus;
	}

	public void setCheckinStatus(Short checkinStatus) {
		this.checkinStatus = checkinStatus;
	}
}
