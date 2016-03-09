package com.simi.vo.record;


public class RecordExpressVo {
	
	private Long id;

    private Long userId;

    private String expressName;

    private String expressNo;

    private Short expressType;

    private Short isDone;

    private Short isClose;

    private Short payType;

    private String fromAddr;

    private String fromName;

    private String fromTel;

    private String toAddr;

    private String toName;

    private String toTel;
    
    private String remarks;

    private String addTimeStr;

    private String updateTimeStr;

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

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

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
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

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromTel() {
		return fromTel;
	}

	public void setFromTel(String fromTel) {
		this.fromTel = fromTel;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToTel() {
		return toTel;
	}

	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
 
}
