package com.simi.po.model.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderSenior {
    private Long id;

    private Long userId;
    
    private Long secId;

    private String mobile;

    private String seniorOrderNo;

    private Long seniorTypeId;
    
    private BigDecimal orderMoney;

    private BigDecimal orderPay;

    private Short validDay;

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

	public Short getValidDay() {
		return validDay;
	}

	public void setValidDay(Short validDay) {
		this.validDay = validDay;
	}

	public Long getSecId() {
		return secId;
	}

	public void setSecId(Long secId) {
		this.secId = secId;
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

	public Long getSeniorTypeId() {
		return seniorTypeId;
	}

	public void setSeniorTypeId(Long seniorTypeId) {
		this.seniorTypeId = seniorTypeId;
	}
}