package com.simi.vo.order;


public class OrderExtRecycleListVo {
	
    private Long orderId;
    
    private String orderNo;
    
    private Long userId;

    private String mobile;
    
    private String name;  
    
    private Short recycleType;
    
    private String recycleTypeName;
    
    private String serviceTypeName;
    
    private String addrName;
    
    private String linkMan;
    
    private String linkTel;
    
    private String orderStatusName;
        
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
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

	public Short getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(Short recycleType) {
		this.recycleType = recycleType;
	}

	public String getRecycleTypeName() {
		return recycleTypeName;
	}

	public void setRecycleTypeName(String recycleTypeName) {
		this.recycleTypeName = recycleTypeName;
	}
	
	
}
