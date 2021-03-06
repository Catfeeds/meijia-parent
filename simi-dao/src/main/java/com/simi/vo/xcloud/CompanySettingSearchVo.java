package com.simi.vo.xcloud;

import java.util.List;

public class CompanySettingSearchVo{
	
	private Long companyId;
	
	private Long userId;
		
	private String settingType;
	
	private List<String> settingTypes;
	
	private String name;
	
	private Long updateTime;
	
	private Short isEnable;
	
	private Long cityId;
	
	private Long regionId;
	
	
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getSettingType() {
		return settingType;
	}

	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<String> getSettingTypes() {
		return settingTypes;
	}

	public void setSettingTypes(List<String> settingTypes) {
		this.settingTypes = settingTypes;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

}
