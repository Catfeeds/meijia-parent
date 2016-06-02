package com.simi.po.dao.feed;

import java.util.List;

import com.simi.po.model.feed.FeedImgs;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedImgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeedImgs record);

    int insertSelective(FeedImgs record);

    FeedImgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeedImgs record);

    int updateByPrimaryKey(FeedImgs record);

	List<FeedImgs> selectBySearchVo(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);
}