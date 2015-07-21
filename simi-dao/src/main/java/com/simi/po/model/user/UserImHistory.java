package com.simi.po.model.user;

public class UserImHistory {
    private Long id;

    private String fromImUser;

    private String toImUser;

    private String msgId;

    private String chatType;

    private String content;

    private String uuid;

    private Long addTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}