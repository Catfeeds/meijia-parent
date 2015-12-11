package com.simi.po.model.partners;

import java.math.BigDecimal;
import java.util.Date;

public class Partners {
    private Long partnerId;
    
    private Long spiderPartnerId;

    private String companyName;
    
    private short registerType;

    private String shortName;
    
	private Date registerTime;
	
    private String addr;

    private Short companySize;

    private String companyLogo;

    private Short isDoor;

    private String keywords;

    private Short status;

    private String statusRemark;

    private String weixin;

    private String qq;

    private String email;

    private Long provinceId;

    private Long cityId;

    private Short isCooperate;

    private String fax;

    private Short payType;

    private BigDecimal discout;

    private String remark;

    private Long addTime;

    private Long adminId;

    private Long updateTime;

    private String businessDesc;
    
    private String creditFileUrl;

    private String website;

    private String spiderUrl;
    
    private String serviceArea;

    private String serviceType;
    
    private String companyDesc;
    
    private String companyDescImg;

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getSpiderPartnerId() {
		return spiderPartnerId;
	}

	public void setSpiderPartnerId(Long spiderPartnerId) {
		this.spiderPartnerId = spiderPartnerId;
	}

	public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public short getRegisterType() {
		return registerType;
	}

	public void setRegisterType(short registerType) {
		this.registerType = registerType;
	}

	public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public Short getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Short companySize) {
        this.companySize = companySize;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo == null ? null : companyLogo.trim();
    }

    public Short getIsDoor() {
        return isDoor;
    }

    public void setIsDoor(Short isDoor) {
        this.isDoor = isDoor;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark == null ? null : statusRemark.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public Short getIsCooperate() {
        return isCooperate;
    }

    public void setIsCooperate(Short isCooperate) {
        this.isCooperate = isCooperate;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Short getPayType() {
        return payType;
    }

    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public BigDecimal getDiscout() {
        return discout;
    }

    public void setDiscout(BigDecimal discout) {
        this.discout = discout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc == null ? null : businessDesc.trim();
    }

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCreditFileUrl() {
		return creditFileUrl;
	}

	public void setCreditFileUrl(String creditFileUrl) {
		this.creditFileUrl = creditFileUrl;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSpiderUrl() {
		return spiderUrl;
	}

	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getCompanyDesc() {
		return companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}

	public String getCompanyDescImg() {
		return companyDescImg;
	}

	public void setCompanyDescImg(String companyDescImg) {
		this.companyDescImg = companyDescImg;
	}
	
    
}