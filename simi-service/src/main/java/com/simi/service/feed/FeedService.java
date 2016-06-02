package com.simi.service.feed;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.feed.Feeds;
import com.simi.vo.feed.FeedListVo;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedService {

	Feeds initFeeds();

	FeedListVo changeToFeedListVo(Feeds item);
	
	int updateByPrimaryKey(Feeds record);

	int updateByPrimaryKeySelective(Feeds record);

	int deleteByPrimaryKey(Long id);

	Long insert(Feeds record);

	Long insertSelective(Feeds record);

	Feeds selectByPrimaryKey(Long id);

	PageInfo selectByListPage(FeedSearchVo vo, int pageNo, int pageSize);

	int updateByTotalView(Long fid);

	

	
}
