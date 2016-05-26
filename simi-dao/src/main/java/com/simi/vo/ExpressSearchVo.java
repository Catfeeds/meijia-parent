package com.simi.vo;

public class ExpressSearchVo {
	
	private Long expressId;
	
	private Short isHot;
	
	private String ecode;
	
	private Long updateTime;

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public Short getIsHot() {
		return isHot;
	}

	public void setIsHot(Short isHot) {
		this.isHot = isHot;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
}
