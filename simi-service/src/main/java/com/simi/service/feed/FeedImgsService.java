package com.simi.service.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;

public interface FeedImgsService {

	int insert(FeedImgs record);

	List<FeedImgs> selectByFid(Long fid);

	FeedImgs initFeedImgs();

	int deleteByFid(Long fid);

}
