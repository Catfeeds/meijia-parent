package com.simi.vo.record;

import java.util.Date;

public class RecordExpressSearchVo {
	
    private Long companyId;

    private Long userId;

    private Long expressId;

    private String expressNo;

    private Short expressType;

    private Short isDone;

    private Short isClose;

    private Short payType;
    
    private String startDate;
    
    private String endDate;
    
    private Long startTime;
    
    private Long endTime;

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

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Short getExpressType() {
		return expressType;
	}

	public void setExpressType(Short expressType) {
		this.expressType = expressType;
	}

	public Short getIsDone() {
		return isDone;
	}

	public void setIsDone(Short isDone) {
		this.isDone = isDone;
	}

	public Short getIsClose() {
		return isClose;
	}

	public void setIsClose(Short isClose) {
		this.isClose = isClose;
	}

	public Short getPayType() {
		return payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
