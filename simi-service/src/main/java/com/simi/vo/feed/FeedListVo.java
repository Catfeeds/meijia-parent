package com.simi.vo.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;
import com.simi.vo.TagVo;

public class FeedListVo {
	
	private Long fid;
		
	private String title;
	
	private Long userId;
	
	private String name;
	
	private String headImg;
		
	private String addTimeStr;
	
	private int totalZan;
	
	private int totalComment;	
	
	private int totalView;
	
	private String feedExtra;
	
	private List<FeedImgs> feedImgs;
	
	private List<TagVo> feedTags;

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public int getTotalZan() {
		return totalZan;
	}

	public void setTotalZan(int totalZan) {
		this.totalZan = totalZan;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public List<FeedImgs> getFeedImgs() {
		return feedImgs;
	}

	public void setFeedImgs(List<FeedImgs> feedImgs) {
		this.feedImgs = feedImgs;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getTotalView() {
		return totalView;
	}

	public void setTotalView(int totalView) {
		this.totalView = totalView;
	}

	public List<TagVo> getFeedTags() {
		return feedTags;
	}

	public void setFeedTags(List<TagVo> feedTags) {
		this.feedTags = feedTags;
	}

	public String getFeedExtra() {
		return feedExtra;
	}

	public void setFeedExtra(String feedExtra) {
		this.feedExtra = feedExtra;
	}
	
}
