package com.simi.vo.resume;

public class ResumeChangeSearchVo {
	
	private Long userId;
	
	private Short limitDay;
	
	private Long hrDictId;
	
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

	public Long getHrDictId() {
		return hrDictId;
	}

	public void setHrDictId(Long hrDictId) {
		this.hrDictId = hrDictId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
