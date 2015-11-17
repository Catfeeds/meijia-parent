package com.simi.vo.user;

import java.math.BigDecimal;

public class UserCouponsVo {

    private Long userId;
    //优惠券Id
    private Long couponId;

    private String introduction;
    
    private String description;
    
    private String cardPasswd;
    
    private BigDecimal value;
    
    private BigDecimal maxValue;
    
    //过期时间，yyyy-MM-dd
    private String toDate;
    
    // 服务类型ID
    private Long serviceTypeId;
    
    private String serviceTypeName;
    
    //服务报价ID
    private Long servicePriceId;
    
    private String servicePriceName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCardPasswd() {
		return cardPasswd;
	}

	public void setCardPasswd(String cardPasswd) {
		this.cardPasswd = cardPasswd;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public String getServicePriceName() {
		return servicePriceName;
	}

	public void setServicePriceName(String servicePriceName) {
		this.servicePriceName = servicePriceName;
	}
    


    
}