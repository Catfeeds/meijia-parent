package com.simi.po.model.user;

import java.util.Date;

public class UserReminds {
    private Long id;

	private Long userId;

	private String mobile;

	private String remindTitle;

	private Date startDate;

	private String startTime;

	private Short cycleType;

	private String crond;

	private String remindToName;

	private String remindToMobile;

	private String remarks;

	private Short isExecuted;

	private Long lastExecuted;

	private Long addTime;

	private Long updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getRemindTitle() {
		return remindTitle;
	}

	public void setRemindTitle(String remindTitle) {
		this.remindTitle = remindTitle == null ? null : remindTitle.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime == null ? null : startTime.trim();
	}

	public String getCrond() {
		return crond;
	}

	public void setCrond(String crond) {
		this.crond = crond == null ? null : crond.trim();
	}
	public String getRemindToName() {
		return remindToName;
	}

	public void setRemindToName(String remindToName) {
		this.remindToName = remindToName == null ? null : remindToName.trim();
	}

	public String getRemindToMobile() {
		return remindToMobile;
	}

	public void setRemindToMobile(String remindToMobile) {
		this.remindToMobile = remindToMobile == null ? null : remindToMobile
				.trim();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public Short getIsExecuted() {
		return isExecuted;
	}

	public void setIsExecuted(Short isExecuted) {
		this.isExecuted = isExecuted;
	}

	public Long getLastExecuted() {
		return lastExecuted;
	}

	public void setLastExecuted(Long lastExecuted) {
		this.lastExecuted = lastExecuted;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public UserReminds() {
		super();
	}

	public Short getCycleType() {
		return cycleType;
	}

	public void setCycleType(Short cycleType) {
		this.cycleType = cycleType;
	}
}