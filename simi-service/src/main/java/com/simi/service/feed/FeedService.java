package com.simi.service.feed;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.feed.Feeds;
import com.simi.vo.feed.FeedListVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedViewVo;



public interface FeedService {

	Feeds initFeeds();

	FeedViewVo changeToFeedViewVo(Feeds feed);

	List<FeedListVo> changeToFeedListVo(List<Feeds> feeds);

	int updateByPrimaryKey(Feeds record);

	int updateByPrimaryKeySelective(Feeds record);

	int deleteByPrimaryKey(Long id);

	Long insert(Feeds record);

	Long insertSelective(Feeds record);

	Feeds selectByPrimaryKey(Long id);

	PageInfo selectByListPage(FeedSearchVo vo, int pageNo, int pageSize);

	int updateByTotalView(Long fid);

	
}
