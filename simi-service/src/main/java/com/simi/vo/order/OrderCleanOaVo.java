package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.vo.OrdersListVo;

public class OrderCleanOaVo extends OrdersListVo{
	
	private Long servicePriceId;
	
	private String companyName;
	
	private Short cleanArea;
	
	private Short cleanType;
	
	private String linkMan;
	
	private String linkTel;
	
	private String partnerOrderNo;
	 
	private BigDecimal partnerOrderMoney;
	
	private Long partnerId;
	
	private Short orderExtStatus;

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

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
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

	public Short getCleanArea() {
		return cleanArea;
	}

	public void setCleanArea(Short cleanArea) {
		this.cleanArea = cleanArea;
	}

	public Short getCleanType() {
		return cleanType;
	}

	public void setCleanType(Short cleanType) {
		this.cleanType = cleanType;
	}
	
}