package com.simi.po.model.card;

public class CardAttend {
    private Long id;

    private Long cardId;

    private Long userId;

    private String mobile;

    private String name;
    
    private Short localAlarm;
    
    private Long lastAlarmTime;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Short getLocalAlarm() {
		return localAlarm;
	}

	public void setLocalAlarm(Short localAlarm) {
		this.localAlarm = localAlarm;
	}

	public Long getLastAlarmTime() {
		return lastAlarmTime;
	}

	public void setLastAlarmTime(Long lastAlarmTime) {
		this.lastAlarmTime = lastAlarmTime;
	}
}