package com.simi.po.model.xcloud;

public class Xcompany {
    private Long companyId;
    
    private Short companyType;

    private String companyName;
    
    private String shortName;

    private Short companySize;

    private Long companyTrade;

    private Long cityId;

    private String lat;

    private String lng;

    private String addr;

    private String invitationCode;
    
    private String qrCode;

    private String linkMan;

    private String email;

    private Long addTime;

    private Long updateTime;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Short getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Short companySize) {
        this.companySize = companySize;
    }

    public Long getCompanyTrade() {
        return companyTrade;
    }

    public void setCompanyTrade(Long companyTrade) {
        this.companyTrade = companyTrade;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Short getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Short companyType) {
		this.companyType = companyType;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}