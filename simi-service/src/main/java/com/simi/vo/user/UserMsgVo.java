package com.simi.vo.user;

import com.simi.po.model.user.UserMsg;

public class UserMsgVo extends UserMsg{
    private String title;

    private String summary;

    private String htmlUrl;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}


}
