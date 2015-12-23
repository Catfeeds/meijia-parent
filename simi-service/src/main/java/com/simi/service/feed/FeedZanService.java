package com.simi.service.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedZan;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedZanViewVo;

public interface FeedZanService {

	FeedZan initFeedZan();

	FeedZan selectByPrimaryKey(Long id);

	int totalByFid(Long fid);

	List<HashMap> totalByFids(List<Long> fids);

	List<FeedZanViewVo> getByTop10(Long fid);

	int insertSelective(FeedZan record);

	int updateByPrimaryKey(FeedZan record);

	int updateByPrimaryKeySelective(FeedZan record);

	int deleteByPrimaryKey(Long id);

	int insert(FeedZan record);

	FeedZan selectBySearchVo(FeedSearchVo searchVo);

	int deleteByFid(Long fid);

}
