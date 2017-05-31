package com.simi.vo.user;

public class UserDetailScoreSearchVo {

	private Long userId;
	
	private String mobile;
	
	private String action;
	
	private Short isConsume;
	
	private String params;
	
	private Long startTime;

	private Long endTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Short getIsConsume() {
		return isConsume;
	}

	public void setIsConsume(Short isConsume) {
		this.isConsume = isConsume;
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}


}
