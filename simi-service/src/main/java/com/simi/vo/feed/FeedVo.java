package com.simi.vo.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.Feeds;
import com.simi.vo.TagVo;

public class FeedVo extends Feeds {
	
	private String name;
	
	private String headImg;
	
	private String addTimeStr;
	
	private int totalZan;
	
	private int totalComment;	
	
	private int totalView;
	
	private String feedExtra;
	
	private List<FeedImgs> feedImgs;
	
	private List<TagVo> feedTags;
	
	private String statusName;

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

	@Override
	public int getTotalView() {
		return totalView;
	}

	@Override
	public void setTotalView(int totalView) {
		this.totalView = totalView;
	}

	@Override
	public String getFeedExtra() {
		return feedExtra;
	}

	@Override
	public void setFeedExtra(String feedExtra) {
		this.feedExtra = feedExtra;
	}

	public List<FeedImgs> getFeedImgs() {
		return feedImgs;
	}

	public void setFeedImgs(List<FeedImgs> feedImgs) {
		this.feedImgs = feedImgs;
	}

	public List<TagVo> getFeedTags() {
		return feedTags;
	}

	public void setFeedTags(List<TagVo> feedTags) {
		this.feedTags = feedTags;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

}
