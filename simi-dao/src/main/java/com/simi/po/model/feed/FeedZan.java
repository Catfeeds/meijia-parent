package com.simi.po.model.feed;

public class FeedZan {
    private Long id;

    private Long fid;

    private Long userId;
    
    private Short feedType;
    
    private Long commentId;

    private Long addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public Short getFeedType() {
		return feedType;
	}

	public void setFeedType(Short feedType) {
		this.feedType = feedType;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}