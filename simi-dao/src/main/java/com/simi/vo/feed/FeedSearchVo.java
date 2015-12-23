package com.simi.vo.feed;

import java.util.List;

public class FeedSearchVo {

	private Long fid;
	
	private Long userId;
	
	private List<Long> userIds;
	
	private Short feedFrom;

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Short getFeedFrom() {
		return feedFrom;
	}

	public void setFeedFrom(Short feedFrom) {
		this.feedFrom = feedFrom;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}
		


}
