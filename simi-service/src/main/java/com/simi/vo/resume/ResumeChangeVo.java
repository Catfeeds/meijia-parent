package com.simi.vo.resume;

import java.util.Map;

import com.simi.po.model.resume.HrResumeChange;

/**
 *
 * @author :hulj
 * @Date : 2016年4月28日下午5:34:54
 * @Description: 
 *		
 *		简历交换 VO
 *			
 */
public class ResumeChangeVo extends HrResumeChange {
	
	private String userName;		//登录用户 姓名
	
	//form页 字段
	private Map<Long, String> timeMap;	// 有效期下拉选择
	
	//列表页字段
	private String endTimeStr;	//截止日期
	
	//detail页字段
	private String limitDayStr;	//有效时间
	
	
	public String getLimitDayStr() {
		return limitDayStr;
	}

	public void setLimitDayStr(String limitDayStr) {
		this.limitDayStr = limitDayStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
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
