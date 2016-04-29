package com.simi.vo.resume;

/**
 *
 * @author :hulj
 * @Date : 2016年4月28日下午4:24:31
 * @Description: 
 *
 */
public class ResumeSearchVo {
	
	private Long userId;
	
	private Short limitDay;

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
	
}
