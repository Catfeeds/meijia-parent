package com.simi.po.model.user;

public class UserImLast {
    private Long id;

    private Long fromUserId;
    
    private Long toUserId;

    private String fromImUser;

    private String toImUser;

    private String msgId;

    private String chatType;

    private String uuid;

    private Long addTime;

    private String imContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromImUser() {
        return fromImUser;
    }

    public void setFromImUser(String fromImUser) {
        this.fromImUser = fromImUser == null ? null : fromImUser.trim();
    }

    public String getToImUser() {
        return toImUser;
    }

    public void setToImUser(String toImUser) {
        this.toImUser = toImUser == null ? null : toImUser.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType == null ? null : chatType.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getImContent() {
        return imContent;
    }

    public void setImContent(String imContent) {
        this.imContent = imContent == null ? null : imContent.trim();
    }

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
}