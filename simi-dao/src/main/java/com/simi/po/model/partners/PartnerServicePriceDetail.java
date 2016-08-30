package com.simi.po.model.partners;

import java.math.BigDecimal;

public class PartnerServicePriceDetail {
    private Long id;

    private Long servicePriceId;

    private String serviceTitle;

    private Long userId;

    private String imgUrl;

    private BigDecimal price;

    private BigDecimal disPrice;

    private String contentStandard;

    private String contentDesc;

    private String contentFlow;

    private Long addTime;
    
    private Short orderType;
    
    private Short orderDuration;
    
    private String videoUrl;
    
    private String category;
    
    private String action;
    
    private String params;
    
    private String gotoUrl;
    
    private Long extendId;
    
    private Short isAddr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(Long servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle == null ? null : serviceTitle.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

 

    public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

    public String getContentStandard() {
        return contentStandard;
    }

    public void setContentStandard(String contentStandard) {
        this.contentStandard = contentStandard == null ? null : contentStandard.trim();
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc == null ? null : contentDesc.trim();
    }

    public String getContentFlow() {
        return contentFlow;
    }

    public void setContentFlow(String contentFlow) {
        this.contentFlow = contentFlow == null ? null : contentFlow.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Short getOrderType() {
		return orderType;
	}

	public void setOrderType(Short orderType) {
		this.orderType = orderType;
	}

	public Short getOrderDuration() {
		return orderDuration;
	}

	public void setOrderDuration(Short orderDuration) {
		this.orderDuration = orderDuration;
	}

	public Short getIsAddr() {
		return isAddr;
	}

	public void setIsAddr(Short isAddr) {
		this.isAddr = isAddr;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Long getExtendId() {
		return extendId;
	}

	public void setExtendId(Long extendId) {
		this.extendId = extendId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getGotoUrl() {
		return gotoUrl;
	}

	public void setGotoUrl(String gotoUrl) {
		this.gotoUrl = gotoUrl;
	}
}