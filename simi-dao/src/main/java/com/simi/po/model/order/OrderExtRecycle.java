package com.simi.po.model.order;


public class OrderExtRecycle {
	
	private Long id;
	
	private Long orderId;
    
    private String orderNo;
    
    private Short orderExtStatus;
    
    private Short recycleType;
    
    private Long userId;
    
    private String mobile;
    
    private String linkMan;

    private String linkTel;
    
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
		this.orderNo = orderNo;
	}

	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}

	public Short getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(Short recycleType) {
		this.recycleType = recycleType;
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
		this.mobile = mobile;
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

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

}