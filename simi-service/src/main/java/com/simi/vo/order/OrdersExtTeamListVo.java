package com.simi.vo.order;

public class OrdersExtTeamListVo {
	
	
	private Long orderId;
	    
	private String orderNo;
	    
	private Long userId;
	
	private String name;
	
	private Short serviceDays;
	
	private Short teamType;
	
	private Long attendNum;
	
	private String cityName;
	
    private String linkMan;

    private String linkTel;
    
    private String orderStatusName;
    
    private Short orderExtStatus;
	
	private String addTimeStr;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getServiceDays() {
		return serviceDays;
	}

	public void setServiceDays(Short serviceDays) {
		this.serviceDays = serviceDays;
	}

	public Short getTeamType() {
		return teamType;
	}

	public void setTeamType(Short teamType) {
		this.teamType = teamType;
	}

	public Long getAttendNum() {
		return attendNum;
	}

	public void setAttendNum(Long attendNum) {
		this.attendNum = attendNum;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}
	
	
	
}