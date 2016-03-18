package com.simi.vo.user;

import com.simi.po.model.user.Users;

public class UserPushBindVo extends Users {

    private String appId;

    private String channelId;

    private String appUserId;
    
    private String clientId;
    
    private Short isNewUser;
    
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Short getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Short isNewUser) {
		this.isNewUser = isNewUser;
	}
}
