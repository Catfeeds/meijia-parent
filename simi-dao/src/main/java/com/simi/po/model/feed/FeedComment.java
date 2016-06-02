package com.simi.po.model.feed;

public class FeedComment {
    private Long id;

    private Long fid;

    private Long userId;
    
    private Short feedType;

    private String comment;
    
    private Short status;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
}