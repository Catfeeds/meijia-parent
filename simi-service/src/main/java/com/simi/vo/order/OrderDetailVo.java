package com.simi.vo.order;

import java.math.BigDecimal;

public class OrderDetailVo extends OrderListVo {
	
    private String cityName;
    
    private String payTypeName;
    
    private String remarks;
    
    private String serviceContent;

	private Long userCouponId;
	
	private String userCouponName;
	
	private BigDecimal userCouponValue;
	
	private String orderExtra;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}

	public Long getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(Long userCouponId) {
		this.userCouponId = userCouponId;
	}

	public String getUserCouponName() {
		return userCouponName;
	}

	public void setUserCouponName(String userCouponName) {
		this.userCouponName = userCouponName;
	}

	public BigDecimal getUserCouponValue() {
		return userCouponValue;
	}

	public void setUserCouponValue(BigDecimal userCouponValue) {
		this.userCouponValue = userCouponValue;
	}

	public String getOrderExtra() {
		return orderExtra;
	}

	public void setOrderExtra(String orderExtra) {
		this.orderExtra = orderExtra;
	}


}
