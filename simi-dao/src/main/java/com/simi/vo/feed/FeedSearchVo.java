package com.simi.vo.feed;

import java.util.List;

public class FeedSearchVo {

	private Long fid;
	
	private List<Long> fids;
	
	private Long userId;
	
	private Short feedType;
	
	private List<Long> userIds;
	
	private Short feedFrom;
	
	private Short status;
	
	private Long tagId;
	
	private Long commentId;

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

	public Short getFeedType() {
		return feedType;
	}

	public void setFeedType(Short feedType) {
		this.feedType = feedType;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public List<Long> getFids() {
		return fids;
	}

	public void setFids(List<Long> fids) {
		this.fids = fids;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
		


}
