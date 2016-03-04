package com.simi.vo;

import java.math.BigDecimal;

public class OrdersGreenPartnerVo extends OrdersListVo{
	
	private Long servicePriceId;
	
	private Short orderExtStatus;
	
	private Short recycleType;
	
	private String linkMan;
	    
	private String linkTel;
	
	private String partnerOrderNo;
	 
	private BigDecimal partnerOrderMoney;
	
	private Long partnerId;

	
	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
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

	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}

	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
	}

	public BigDecimal getPartnerOrderMoney() {
		return partnerOrderMoney;
	}

	public void setPartnerOrderMoney(BigDecimal partnerOrderMoney) {
		this.partnerOrderMoney = partnerOrderMoney;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	
}