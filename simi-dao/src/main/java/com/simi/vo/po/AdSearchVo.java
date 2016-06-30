package com.simi.vo.po;

public class AdSearchVo {

	private String title;
	
	private String appType;
	
	private String adType;
	
	private String serviceTypeIds;
	
	private Long updateTime;
	
	private Short enable;

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(String serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}
	

}
