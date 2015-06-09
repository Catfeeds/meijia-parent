package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.po.model.order.Orders;

public class OrderViewVo extends Orders {

	    private String userName;

	    private String userAddrs;

	    private Short payType;

	    private Short cleanTools;

	    private BigDecimal cleanToolsPrice;

	    private BigDecimal priceHour;

	    private BigDecimal priceHourDiscount;

	    private BigDecimal orderMoney;

	    private BigDecimal orderPay;

	    private String staffName;

	    private String staffMobile;

	    private String dispatchRemarks;

	    private Long partnerId;

	    private String orderRefNo;

	    private Long dispatchTime;

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

		public Short getCleanTools() {
			return cleanTools;
		}

		public void setCleanTools(Short cleanTools) {
			this.cleanTools = cleanTools;
		}

		public BigDecimal getCleanToolsPrice() {
			return cleanToolsPrice;
		}

		public void setCleanToolsPrice(BigDecimal cleanToolsPrice) {
			this.cleanToolsPrice = cleanToolsPrice;
		}

		public BigDecimal getPriceHour() {
			return priceHour;
		}

		public void setPriceHour(BigDecimal priceHour) {
			this.priceHour = priceHour;
		}

		public BigDecimal getPriceHourDiscount() {
			return priceHourDiscount;
		}

		public void setPriceHourDiscount(BigDecimal priceHourDiscount) {
			this.priceHourDiscount = priceHourDiscount;
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

		public String getStaffName() {
			return staffName;
		}

		public void setStaffName(String staffName) {
			this.staffName = staffName;
		}

		public String getStaffMobile() {
			return staffMobile;
		}

		public void setStaffMobile(String staffMobile) {
			this.staffMobile = staffMobile;
		}

		public String getDispatchRemarks() {
			return dispatchRemarks;
		}

		public void setDispatchRemarks(String dispatchRemarks) {
			this.dispatchRemarks = dispatchRemarks;
		}

		public Long getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(Long partnerId) {
			this.partnerId = partnerId;
		}

		public String getOrderRefNo() {
			return orderRefNo;
		}

		public void setOrderRefNo(String orderRefNo) {
			this.orderRefNo = orderRefNo;
		}

		public Long getDispatchTime() {
			return dispatchTime;
		}

		public void setDispatchTime(Long dispatchTime) {
			this.dispatchTime = dispatchTime;
		}

		public String getCardPasswd() {
			return cardPasswd;
		}

		public void setCardPasswd(String cardPasswd) {
			this.cardPasswd = cardPasswd;
		}




}
