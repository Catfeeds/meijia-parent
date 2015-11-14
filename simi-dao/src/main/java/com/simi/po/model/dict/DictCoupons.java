package com.simi.po.model.dict;

import java.math.BigDecimal;
import java.util.Date;

public class DictCoupons {
    private Long id;

	private String cardNo;

	private String cardPasswd;

	private BigDecimal value;
	
	private BigDecimal maxValue;

	private Short couponType;

	private Short rangType;

	private Short rangFrom;

	private long serviceTypeId;
	
	private long servicePriceId;

	private String introduction;

	private String description;
	
	private Short rangMonth;

	private Date fromDate;

	private Date toDate;

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
	
	public long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(long servicePriceId) {
		this.servicePriceId = servicePriceId;
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

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public Short getRangMonth() {
		return rangMonth;
	}

	public void setRangMonth(Short rangMonth) {
		this.rangMonth = rangMonth;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}