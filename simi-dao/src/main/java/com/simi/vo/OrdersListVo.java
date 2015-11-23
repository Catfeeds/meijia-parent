package com.simi.vo;

import java.math.BigDecimal;

import com.simi.po.model.order.Orders;

public class OrdersListVo extends Orders{
	
	private String userName;
	
	private String ServiceTypeName;
	
	private String addr;
	
	private String partnerUserName;
	
	private String orderStatusName;
	
	private BigDecimal orderMoney;

	private BigDecimal orderPay;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServiceTypeName() {
		return ServiceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		ServiceTypeName = serviceTypeName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPartnerUserName() {
		return partnerUserName;
	}

	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
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



}