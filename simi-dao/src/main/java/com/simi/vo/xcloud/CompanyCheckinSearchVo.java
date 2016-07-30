package com.simi.vo.xcloud;

public class CompanyCheckinSearchVo{
	
	private Long companyId;
		
	private Long userId;
	
	private String mobile;
	
	private Long staffId;
	
	private Short checkinFrom;
	
	private Short checkinType;
	
	private Short checkinStatus;
	
	private Long startTime;
	
	private Long endTime;
	
	private int cyear;
	
	private int cmonth;
	
	private Long cday;
	
	private String selectDay;
	
	
		
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

	public int getCyear() {
		return cyear;
	}

	public void setCyear(int cyear) {
		this.cyear = cyear;
	}

	public int getCmonth() {
		return cmonth;
	}

	public void setCmonth(int cmonth) {
		this.cmonth = cmonth;
	}

	public Long getCday() {
		return cday;
	}

	public void setCday(Long cday) {
		this.cday = cday;
	}

	public String getSelectDay() {
		return selectDay;
	}

	public void setSelectDay(String selectDay) {
		this.selectDay = selectDay;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
