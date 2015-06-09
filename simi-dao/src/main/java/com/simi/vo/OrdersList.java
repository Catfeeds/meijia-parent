package com.simi.vo;

import java.math.BigDecimal;

public class OrdersList {
    private Long id;

    private Long orderId;

	private Long userId;

    private String mobile;
    
    private String customName;
    
    private String agentMobile;

    private Long cityId;

    private String orderNo;

    private Short serviceType;
    
    private BigDecimal orderMoney;

    private Long serviceDate;
    
	/**
	 * 因为之前serviceDate 拼写错误为 servieDate 所以导致修正后app有错，保留servidDate
	 */
    private Long servieDate;

    private Long startTime;

    private Short serviceHours;

    private Long cellId;

    private Long addrId;

    private String remarks;

    private String cellName;
    
    private Short orderFrom;

    private Short orderStatus;

    private Short orderRate;

    private String orderRateContent;

    private Long addTime;

    private Long updateTime;

    private String addr;
    
    private String name;
    
    public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Short getServiceType() {
        return serviceType;
    }

    public void setServiceType(Short serviceType) {
        this.serviceType = serviceType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Short getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(Short serviceHours) {
        this.serviceHours = serviceHours;
    }

    public Long getCellId() {
        return cellId;
    }

    public void setCellId(Long cellId) {
        this.cellId = cellId;
    }

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public Short getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Short orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Short getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(Short orderRate) {
        this.orderRate = orderRate;
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

	public String getOrderRateContent() {
		return orderRateContent;
	}

	public void setOrderRateContent(String orderRateContent) {
		this.orderRateContent = orderRateContent;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}    
    public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getAgentMobile() {
		return agentMobile;
	}

	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}

	public Long getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Long serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Long getServieDate() {
		return servieDate;
	}

	public void setServieDate(Long servieDate) {
		this.servieDate = servieDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}