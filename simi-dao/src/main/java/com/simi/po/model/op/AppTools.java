package com.simi.po.model.op;

public class AppTools {
    private Long tId;

    private Short No;

    private String name;

    private String logo;

    private String appType;
    
    private String menuType;

    private String openType;

    private String url;
    
    private String action;
    
    private String params;
    
    private Short isDefault;

    private Short isDel;
    
    private Short isPartner;
    
    private Short isOnline;
    
    private String appProvider;
    
    private String appDescribe;

    private String authUrl;
    
    private String qrCode;

    private long addTime;
    
    private Long updateTime;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Short getNo() {
        return No;
    }

    public void setNo(Short No) {
        this.No = No;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType == null ? null : openType.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	public Short getIsDel() {
		return isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	public Short getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Short isPartner) {
        this.isPartner = isPartner;
    }

    public Short getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Short isOnline) {
		this.isOnline = isOnline;
	}

	public String getAppProvider() {
		return appProvider;
	}

	public void setAppProvider(String appProvider) {
		this.appProvider = appProvider;
	}

	public String getAppDescribe() {
		return appDescribe;
	}

	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}

	public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl == null ? null : authUrl.trim();
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}