package com.simi.po.model.dict;

public class DictExpress {
    private Long expressId;
    
    private String ecode;

    private String name;
    
    private Short  isHot;

    private String website;

    private String apiOrderUrl;

    private String apiSearchUrl;

    private Long addTime;

    private Long updateTime;

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getApiOrderUrl() {
        return apiOrderUrl;
    }

    public void setApiOrderUrl(String apiOrderUrl) {
        this.apiOrderUrl = apiOrderUrl == null ? null : apiOrderUrl.trim();
    }

    public String getApiSearchUrl() {
        return apiSearchUrl;
    }

    public void setApiSearchUrl(String apiSearchUrl) {
        this.apiSearchUrl = apiSearchUrl == null ? null : apiSearchUrl.trim();
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

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public Short getIsHot() {
		return isHot;
	}

	public void setIsHot(Short isHot) {
		this.isHot = isHot;
	}
}