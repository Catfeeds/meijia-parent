package com.simi.service.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedZan;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedZanViewVo;

public interface FeedZanService {

	FeedZan initFeedZan();

	FeedZan selectByPrimaryKey(Long id);

	int totalByFid(FeedSearchVo searchVo);

	List<HashMap> totalByFids(FeedSearchVo searchVo);

	List<FeedZanViewVo> getByTop10(FeedSearchVo searchVo);

	int insertSelective(FeedZan record);

	int updateByPrimaryKey(FeedZan record);

	int updateByPrimaryKeySelective(FeedZan record);

	int deleteByPrimaryKey(Long id);

	int insert(FeedZan record);

	List<FeedZan> selectBySearchVo(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);

}
