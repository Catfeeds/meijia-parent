package com.simi.vo.order;

import java.math.BigDecimal;


public class OrderExtWaterListVo {
	
    private Long orderId;
    
    private String orderNo;
    
    private Long userId;
        
    private String serviceTypeName;
    
    private String addrName;
    
    private String servicePriceName;
    
    private String imgUrl;
    
    private BigDecimal disPrice;
    
    private int serviceNum;
    
    private String orderStatusName;
    
    private Short orderExtStatus;
    
    private Short isDone;
    
    private String isDoneTimeStr;
        
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

	public String getServicePriceName() {
		return servicePriceName;
	}

	public void setServicePriceName(String servicePriceName) {
		this.servicePriceName = servicePriceName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public BigDecimal getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(BigDecimal disPrice) {
		this.disPrice = disPrice;
	}

	public Integer getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
	}

	public Short getIsDone() {
		return isDone;
	}

	public void setIsDone(Short isDone) {
		this.isDone = isDone;
	}

	public String getIsDoneTimeStr() {
		return isDoneTimeStr;
	}

	public void setIsDoneTimeStr(String isDoneTimeStr) {
		this.isDoneTimeStr = isDoneTimeStr;
	}

	public void setServiceNum(int serviceNum) {
		this.serviceNum = serviceNum;
	}

	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}
	
	
}
