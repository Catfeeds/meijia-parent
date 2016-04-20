package com.simi.vo.partners;


import java.math.BigDecimal;


public class PartnerServicePriceListVo {
	
	private Long id;
	
	private String name;
	
	private Long serviceTypeId;
	
	private Long servicePriceId;
	
	private String serviceTitle;
	
    private BigDecimal price;

    private BigDecimal disPrice;
    
    private String detailUrl;
    
    private Short isAddr;
    
    private String imgUrl;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getIsAddr() {
		return isAddr;
	}

	public void setIsAddr(Short isAddr) {
		this.isAddr = isAddr;
	}

	public String getServiceTitle() {
		return serviceTitle;
	}

	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	} 
}
