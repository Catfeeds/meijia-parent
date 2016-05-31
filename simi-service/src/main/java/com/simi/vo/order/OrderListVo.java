package com.simi.vo.order;

import java.math.BigDecimal;

public class OrderListVo {
	
    private Long orderId;
    
    private String orderNo;
 
    private Long partnerUserId;
    
    private String partnerUserName;
    
    private String partnerUserHeadImg;

    private Long userId;

    private String mobile;
    
	//用户称呼
	private String name;    
	
	private String headImg;
	
	private Long serviceTypeId;
    
    private String serviceTypeName;
    
    private Long servicePriceId;
    
    private String servicePriceName;
    
    private String serviceTypeImg;
    
    private Short isAddr;
    
    private Long  addrId;
   
  	private String addrName;
  	
  	private Short orderStatus;

    private String orderStatusName;
        
    private String addTimeStr;
	
	private BigDecimal orderMoney;

	private BigDecimal orderPay;
	
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

	public Long getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(Long partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public String getPartnerUserName() {
		return partnerUserName;
	}

	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}

	public String getPartnerUserHeadImg() {
		return partnerUserHeadImg;
	}

	public void setPartnerUserHeadImg(String partnerUserHeadImg) {
		this.partnerUserHeadImg = partnerUserHeadImg;
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

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getOrderPay() {
		return orderPay;
	}

	public void setOrderPay(BigDecimal orderPay) {
		this.orderPay = orderPay;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getIsAddr() {
		return isAddr;
	}

	public void setIsAddr(Short isAddr) {
		this.isAddr = isAddr;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public String getServicePriceName() {
		return servicePriceName;
	}

	public void setServicePriceName(String servicePriceName) {
		this.servicePriceName = servicePriceName;
	}

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getServiceTypeImg() {
		return serviceTypeImg;
	}

	public void setServiceTypeImg(String serviceTypeImg) {
		this.serviceTypeImg = serviceTypeImg;
	}
}
