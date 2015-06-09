package com.simi.vo.user;

import com.simi.po.model.user.Users;

public class UserBaiduBindVo extends Users {

    private String appId;

    private String channelId;

    private String appUserId;

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
}
