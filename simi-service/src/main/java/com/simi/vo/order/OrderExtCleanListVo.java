package com.simi.vo.order;


public class OrderExtCleanListVo {
	
    private Long orderId;
    
    private String orderNo;
    
    private Short orderStatus;
    
    private Short orderExtStatus;
    
    private Long userId;
    
    private String companyName;
        
    private String serviceTypeName;
    
    private String addrName;
        
    private String cleanAreaName;
    
    private String cleanTypeName;
    
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCleanAreaName() {
		return cleanAreaName;
	}

	public void setCleanAreaName(String cleanAreaName) {
		this.cleanAreaName = cleanAreaName;
	}

	public String getCleanTypeName() {
		return cleanTypeName;
	}

	public void setCleanTypeName(String cleanTypeName) {
		this.cleanTypeName = cleanTypeName;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}


}
