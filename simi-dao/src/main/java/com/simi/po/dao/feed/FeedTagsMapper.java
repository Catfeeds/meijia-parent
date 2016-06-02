package com.simi.po.dao.feed;

import java.util.List;

import com.simi.po.model.feed.FeedTags;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedTagsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeedTags record);

    int insertSelective(FeedTags record);

    FeedTags selectByPrimaryKey(Long id);
    
    List<FeedTags> selectBySearchVo(FeedSearchVo searchVo);

    int updateByPrimaryKeySelective(FeedTags record);

    int updateByPrimaryKey(FeedTags record);

	int deleteBySearchVo(FeedSearchVo searchVo);
}