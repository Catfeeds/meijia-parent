package com.simi.service.feed;

import java.util.List;

import com.simi.po.model.feed.FeedTags;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedTagsService {

	FeedTags initFeedTags();

	FeedTags selectByPrimaryKey(Long id);

	int insertSelective(FeedTags record);

	int updateByPrimaryKey(FeedTags record);

	int updateByPrimaryKeySelective(FeedTags record);

	int deleteByPrimaryKey(Long id);

	int insert(FeedTags record);

	List<FeedTags> selectBySearchVo(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);

}
