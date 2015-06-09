package com.simi.vo.user;

import java.math.BigDecimal;

public class UserCouponVo {
    private Long id;

    private Long userId;

    private String mobile;

    private Long couponId;

    private String cardPasswd;

    private BigDecimal value;

    private Short rangeType;

    private Short rangFrom;

    private String serviceType;

    private String introduction;

    private String description;

    private Long expTime;

    private Short isUsed;

    private Long usedTime;

    private String orderNo;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
        this.cardPasswd = cardPasswd == null ? null : cardPasswd.trim();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }

    public Short getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Short isUsed) {
        this.isUsed = isUsed;
    }

    public Long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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

	public Short getRangeType() {
		return rangeType;
	}

	public void setRangeType(Short rangeType) {
		this.rangeType = rangeType;
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
		this.serviceType = serviceType;
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
}