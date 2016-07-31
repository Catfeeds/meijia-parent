package com.simi.vo.user;

import java.util.Date;
import java.util.List;

public class UserLeaveSearchVo {
	
	private Long leaveId;
	
	private Long companyId;
	
	private Long userId;
	
	private String mobile;
	
	private Long passUserId;
		
	private List<Short> status;
	
	private Short leaveType;
	
	private Date startDate;
	
	private Date endDate;

	private Long startTime;

	private Long endTime;
	
	private int cyear;
	
	private int cmonth;

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Short> getStatus() {
		return status;
	}

	public void setStatus(List<Short> status) {
		this.status = status;
	}

	public Short getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Short leaveType) {
		this.leaveType = leaveType;
	}

	public Long getPassUserId() {
		return passUserId;
	}

	public void setPassUserId(Long passUserId) {
		this.passUserId = passUserId;
	}

	public int getCyear() {
		return cyear;
	}

	public void setCyear(int cyear) {
		this.cyear = cyear;
	}

	public int getCmonth() {
		return cmonth;
	}

	public void setCmonth(int cmonth) {
		this.cmonth = cmonth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



}
