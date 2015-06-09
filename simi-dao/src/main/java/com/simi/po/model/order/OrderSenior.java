package com.simi.po.model.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSenior {
    private Long id;

    private Long userId;

    private String mobile;

    private String seniorOrderNo;

    private Long seniorType;

    private BigDecimal seniorPay;

    private Short validMonth;

    private Date startDate;

    private Date endDate;

    private Short payType;

    private Short orderStatus;

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

    public String getSeniorOrderNo() {
        return seniorOrderNo;
    }

    public void setSeniorOrderNo(String seniorOrderNo) {
        this.seniorOrderNo = seniorOrderNo == null ? null : seniorOrderNo.trim();
    }

    public Long getSeniorType() {
        return seniorType;
    }

    public void setSeniorType(Long seniorType) {
        this.seniorType = seniorType;
    }

    public BigDecimal getSeniorPay() {
        return seniorPay;
    }

    public void setSeniorPay(BigDecimal seniorPay) {
        this.seniorPay = seniorPay;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
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

	public Short getPayType() {
		return payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Short getValidMonth() {
		return validMonth;
	}

	public void setValidMonth(Short validMonth) {
		this.validMonth = validMonth;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}