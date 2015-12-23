package com.simi.po.dao.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedComment;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FeedComment record);

    int insertSelective(FeedComment record);

    FeedComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FeedComment record);

    int updateByPrimaryKey(FeedComment record);

	List<FeedComment> selectByListPage(FeedSearchVo searchVo);

	int totalByFid(Long fid);

	List<HashMap> totalByFids(List<Long> fids);

	int deleteByFid(Long fid);
}