package com.simi.vo.xcloud;

import java.util.List;

public class CompanySettingSearchVo{
	
	private Long companyId;
	
	private Long userId;
		
	private String settingType;
	
	private List<String> settingTypes;
	
	private String name;
	
	private Long updateTime;
			
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

}
