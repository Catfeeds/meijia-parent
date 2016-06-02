package com.simi.po.dao.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedZan;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedZanMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeedZan record);

    int insertSelective(FeedZan record);

    FeedZan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeedZan record);

    int updateByPrimaryKey(FeedZan record);

	int totalByFid(FeedSearchVo searchVo);

	List<HashMap> totalByFids(FeedSearchVo searchVo);

	List<FeedZan> getByTop10(FeedSearchVo searchVo);

	List<FeedZan> selectBySearchVo(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);
}