package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.vo.OrdersListVo;

public class OrdersWaterListVo extends OrdersListVo{
	
	private Long servicePriceId;
	
	private String servicePriceName;
	
	private String servicePriceImg;
	
	private Integer serviceNum;
	
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

	public Integer getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
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

	public String getServicePriceName() {
		return servicePriceName;
	}

	public void setServicePriceName(String servicePriceName) {
		this.servicePriceName = servicePriceName;
	}

	public String getServicePriceImg() {
		return servicePriceImg;
	}

	public void setServicePriceImg(String servicePriceImg) {
		this.servicePriceImg = servicePriceImg;
	}
	
}