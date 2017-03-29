package com.simi.vo;

import java.util.List;

public class OrderSearchVo {
	
	private Long partnerUserId;
	
	private Long userId;
	
	private String mobile;
	
	private String orderNo;
	
	private Long orderId;
	
	private String orderType;

	private Long serviceTypeId;
	
	private Short orderFrom;
	
	private Short orderStatus;
	
	private List<Integer> orderStatusIn;
	
	private String action;
	
	private Long  notServiceTypeId;
	
	private Long servicePriceId;
	
	private String orderNum;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public Short getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(Short orderFrom) {
		this.orderFrom = orderFrom;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(Long partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getNotServiceTypeId() {
		return notServiceTypeId;
	}

	public void setNotServiceTypeId(Long notServiceTypeId) {
		this.notServiceTypeId = notServiceTypeId;
	}

	public List<Integer> getOrderStatusIn() {
		return orderStatusIn;
	}

	public void setOrderStatusIn(List<Integer> orderStatusIn) {
		this.orderStatusIn = orderStatusIn;
	}

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
