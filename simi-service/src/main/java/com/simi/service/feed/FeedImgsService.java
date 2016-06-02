package com.simi.service.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedImgsService {

	int insert(FeedImgs record);

	FeedImgs initFeedImgs();

	int deleteBySearchVo(FeedSearchVo searchVo);

	List<FeedImgs> selectBySearchVo(FeedSearchVo searchVo);

}
