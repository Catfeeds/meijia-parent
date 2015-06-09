package com.simi.po.model.dict;

import java.math.BigDecimal;

public class DictCoupons {
    private Long id;

	private String cardNo;

	private String cardPasswd;

	private BigDecimal value;

	private Short couponType;

	private Short rangType;

	private Short rangFrom;

	private String serviceType;

	private String introduction;

	private String description;

	private Long expTime;

	private Long addTime;

	private Long updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getCardPasswd() {
		return cardPasswd;
	}

	public void setCardPasswd(String cardPasswd) {
		this.cardPasswd = cardPasswd == null ? null : cardPasswd.trim();
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Short getRangType() {
		return rangType;
	}

	public void setRangType(Short rangType) {
		this.rangType = rangType;
	}

	public Short getRangFrom() {
		return rangFrom;
	}

	public void setRangFrom(Short rangFrom) {
		this.rangFrom = rangFrom;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType == null ? null : serviceType.trim();
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction == null ? null : introduction.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Long getExpTime() {
		return expTime;
	}

	public void setExpTime(Long expTime) {
		this.expTime = expTime;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Short getCouponType() {
		return couponType;
	}

	public void setCouponType(Short couponType) {
		this.couponType = couponType;
	}
}