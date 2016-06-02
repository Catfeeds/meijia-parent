package com.simi.po.dao.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedComment;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedCommentMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(FeedComment record);

    int insertSelective(FeedComment record);

    FeedComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeedComment record);

    int updateByPrimaryKey(FeedComment record);

	List<FeedComment> selectByListPage(FeedSearchVo searchVo);
	
	List<FeedComment> selectBySearchVo(FeedSearchVo searchVo);

	int totalByFid(FeedSearchVo searchVo);

	List<HashMap> totalByFids(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);
}