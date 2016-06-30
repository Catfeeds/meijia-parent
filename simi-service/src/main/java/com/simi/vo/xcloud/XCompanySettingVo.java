package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanySetting;


public class XCompanySettingVo extends XcompanySetting {
	
	private Long settingId;
	
	private String addTimeStr;
	
	private Object settingValueVo;

	public Object getSettingValueVo() {
		return settingValueVo;
	}

	public void setSettingValueVo(Object settingValueVo) {
		this.settingValueVo = settingValueVo;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Long getSettingId() {
		return settingId;
	}

	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}
	
	

}
