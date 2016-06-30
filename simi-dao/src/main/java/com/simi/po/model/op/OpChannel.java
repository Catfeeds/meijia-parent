package com.simi.po.model.op;

public class OpChannel {
    private Long channelId;
    
    private Short No;
    
    private String appType;

    private String name;
    
    private String channelPosition;

    private Short enable;

    private long addTime;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelPosition() {
		return channelPosition;
	}

	public void setChannelPosition(String channelPosition) {
		this.channelPosition = channelPosition;
	}

	public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Short getNo() {
		return No;
	}

	public void setNo(Short no) {
		No = no;
	}
}