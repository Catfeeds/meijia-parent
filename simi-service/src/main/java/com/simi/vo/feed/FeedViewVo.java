package com.simi.vo.feed;

import java.util.List;

import com.simi.po.model.user.Tags;

public class FeedViewVo extends FeedListVo {

	private List<FeedZanViewVo> zanTop10;
			
	private String feedExtra;
	
	public List<FeedZanViewVo> getZanTop10() {
		return zanTop10;
	}

	public void setZanTop10(List<FeedZanViewVo> zanTop10) {
		this.zanTop10 = zanTop10;
	}

	public String getFeedExtra() {
		return feedExtra;
	}

	public void setFeedExtra(String feedExtra) {
		this.feedExtra = feedExtra;
	}	
}
