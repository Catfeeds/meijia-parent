package com.simi.vo.resume;

import java.util.Map;

import com.simi.po.model.resume.HrJobHunter;

/**
 *
 * @author :hulj
 * @Date : 2016年4月29日下午4:06:22
 * @Description: 
 *
 */
public class JobHunterVo extends HrJobHunter {
	
	private String userName;
	
	//detail页字段
	private String limitDayStr;	//有效时间
	
	//form页 字段
	private Map<Long, String> timeMap;	// 有效期下拉选择
	
	//列表页字段
	private String endTimeStr;	//截止日期
	
	private String endTimeFlag;	// 是否已到 截止日期
	
	
	public String getEndTimeFlag() {
		return endTimeFlag;
	}

	public void setEndTimeFlag(String endTimeFlag) {
		this.endTimeFlag = endTimeFlag;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getLimitDayStr() {
		return limitDayStr;
	}

	public void setLimitDayStr(String limitDayStr) {
		this.limitDayStr = limitDayStr;
	}

	public Map<Long, String> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(Map<Long, String> timeMap) {
		this.timeMap = timeMap;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
