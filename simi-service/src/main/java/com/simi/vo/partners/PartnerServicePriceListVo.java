package com.simi.vo.partners;


import java.math.BigDecimal;

import com.simi.po.model.partners.PartnerServicePriceDetail;


public class PartnerServicePriceListVo extends PartnerServicePriceDetail {
	private String name;
	
	private Long servicePriceId;
	
    private BigDecimal price;

    private BigDecimal disPrice;
    
    private String detailUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(BigDecimal disPrice) {
		this.disPrice = disPrice;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	} 
}
