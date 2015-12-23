package com.simi.po.dao.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;

public interface FeedImgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeedImgs record);

    int insertSelective(FeedImgs record);

    FeedImgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeedImgs record);

    int updateByPrimaryKey(FeedImgs record);

	List<FeedImgs> selectByFid(Long fid);

	int deleteByFid(Long fid);
}