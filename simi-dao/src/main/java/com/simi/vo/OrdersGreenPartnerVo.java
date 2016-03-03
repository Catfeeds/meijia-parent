package com.simi.vo;

import java.math.BigDecimal;

public class OrdersGreenPartnerVo extends OrdersListVo{
	
	private Short orderExtStatus;
	
	private Short recycleType;
	
	private Long totalNum;
	    
	private BigDecimal totalBudget;
	
	private String partnerOrderNo;
	 
	private BigDecimal partnerOrderMoney;
	
	private Long partnerId;

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

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(BigDecimal totalBudget) {
		this.totalBudget = totalBudget;
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