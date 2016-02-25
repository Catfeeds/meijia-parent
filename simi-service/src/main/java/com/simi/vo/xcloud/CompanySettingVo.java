package com.simi.vo.xcloud;


public class CompanySettingVo{

	private Long settingId;
	
	private String name;
	
	private String settingJson;
	//添加时间戳
	private String addTimeStr ;
	public Long getSettingId() {
		return settingId;
	}
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSettingJson() {
		return settingJson;
	}
	public void setSettingJson(String settingJson) {
		this.settingJson = settingJson;
	}
	public String getAddTimeStr() {
		return addTimeStr;
	}
	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
	
}
