package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.vo.OrdersListVo;

public class OrdersTeamListVo extends OrdersListVo{
	
	private Short serviceDays;
	
	private Short teamType;
	
	private Long attendNum;
	
	private Long cityId;
	
	private String cityName;
	
	private String linkMan;
	
	private String linkTel;
	
	private String partnerOrderNo;
	 
	private BigDecimal partnerOrderMoney;
	
	private Long partnerId;
	
	private Short orderExtStatus;

	public Short getServiceDays() {
		return serviceDays;
	}

	public void setServiceDays(Short serviceDays) {
		this.serviceDays = serviceDays;
	}

	public Short getTeamType() {
		return teamType;
	}

	public void setTeamType(Short teamType) {
		this.teamType = teamType;
	}

	public Long getAttendNum() {
		return attendNum;
	}

	public void setAttendNum(Long attendNum) {
		this.attendNum = attendNum;
	}

	@Override
	public Long getCityId() {
		return cityId;
	}

	@Override
	public void setCityId(Long cityId) {
		this.cityId = cityId;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}