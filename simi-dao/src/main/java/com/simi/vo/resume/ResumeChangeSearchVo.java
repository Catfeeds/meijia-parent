package com.simi.vo.resume;

public class ResumeChangeSearchVo {
	
	private Long userId;
	
	private Short limitDay;
	
	private Long jobId;
	
	private String cityName;

	public Short getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(Short limitDay) {
		this.limitDay = limitDay;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	
}
