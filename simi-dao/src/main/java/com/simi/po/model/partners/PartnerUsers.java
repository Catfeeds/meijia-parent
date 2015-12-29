package com.simi.po.model.partners;

import java.math.BigDecimal;

public class PartnerUsers {
    private Long id;

    private Long partnerId;

    private Long userId;
    
    private Long serviceTypeId;
    //权重类型
    private Short weightType;
    //权重排序
    private Short weightNo;
    //最近三个月订单数
    private Short totalOrder;
    //评价-总好评率
    private BigDecimal totalRate;
    //评价-总响应速度
    private BigDecimal totalRateResponse;
    //评价-总服务态度
    private BigDecimal totalRateAttitude;
    //评价-总专业程度
    private BigDecimal totalRateMajor;
    
    private Short responseTime;
    
    private Long provinceId;
    
    private Long cityId;
    
    private Long regionId;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Short getWeightType() {
		return weightType;
	}

	public void setWeightType(Short weightType) {
		this.weightType = weightType;
	}

	public Short getWeightNo() {
		return weightNo;
	}

	public void setWeightNo(Short weightNo) {
		this.weightNo = weightNo;
	}

	public Short getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(Short totalOrder) {
		this.totalOrder = totalOrder;
	}

	public BigDecimal getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getTotalRateResponse() {
		return totalRateResponse;
	}

	public void setTotalRateResponse(BigDecimal totalRateResponse) {
		this.totalRateResponse = totalRateResponse;
	}

	public BigDecimal getTotalRateAttitude() {
		return totalRateAttitude;
	}

	public void setTotalRateAttitude(BigDecimal totalRateAttitude) {
		this.totalRateAttitude = totalRateAttitude;
	}

	public BigDecimal getTotalRateMajor() {
		return totalRateMajor;
	}

	public void setTotalRateMajor(BigDecimal totalRateMajor) {
		this.totalRateMajor = totalRateMajor;
	}

	public Short getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Short responseTime) {
		this.responseTime = responseTime;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
}