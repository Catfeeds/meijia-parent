package com.simi.po.model.promotion;

public class Msg {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private String htmlUrl;
    
    private Integer sendTotal;

    private Short sendGroup;

    private Short isEnable;
    
    private Short sendStatus;
    
    private Long lastSendTime;

    private Long addTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl == null ? null : htmlUrl.trim();
    }

    public Short getSendGroup() {
        return sendGroup;
    }

    public void setSendGroup(Short sendGroup) {
        this.sendGroup = sendGroup;
    }

    public Short getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Short isEnable) {
        this.isEnable = isEnable;
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

	public Integer getSendTotal() {
		return sendTotal;
	}

	public void setSendTotal(Integer sendTotal) {
		this.sendTotal = sendTotal;
	}

	public Short getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Short sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Long getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Long lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	
    
}