package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanySetting;


public class CompanySettingVo extends XcompanySetting {

	private Long settingId;
	
	//添加时间戳
	private String addTimeStr ;
	public Long getSettingId() {
		return settingId;
	}
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}
	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
	
}
