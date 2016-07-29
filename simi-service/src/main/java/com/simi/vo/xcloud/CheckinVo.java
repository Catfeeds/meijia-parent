package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanyCheckin;

public class CheckinVo extends XcompanyCheckin {
	
	private String name;
	
	private String mobile;
	
	private Long settingId;
	
	private String settingName;
	
	private String status;
	
	private String addTimeStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	@Override
	public Long getSettingId() {
		return settingId;
	}

	@Override
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
