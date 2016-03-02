package com.simi.po.model.order;

public class OrderExtTeam {
	
    private Long id;

    private Long orderId;

    private String orderNo;
    
    private Short orderExtStatus;

    private Long userId;

    private String mobile;

    private Short serviceDays;

    private Short teamType;

    private Long attendNum;

    private Long cityId;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}