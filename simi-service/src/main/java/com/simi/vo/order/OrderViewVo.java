package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.po.model.order.Orders;

public class OrderViewVo extends Orders {

	private String userName;

	private String userAddrs;

	private Short payType;

	private BigDecimal orderMoney;

	private BigDecimal orderPay;

	private String cardPasswd;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddrs() {
		return userAddrs;
	}

	public void setUserAddrs(String userAddrs) {
		this.userAddrs = userAddrs;
	}

	public Short getPayType() {
		return payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
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

	public String getCardPasswd() {
		return cardPasswd;
	}

	public void setCardPasswd(String cardPasswd) {
		this.cardPasswd = cardPasswd;
	}
}
