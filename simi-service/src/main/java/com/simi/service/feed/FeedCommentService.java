package com.simi.service.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedComment;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedCommentService {

	FeedComment initFeedComment();

	FeedComment selectByPrimaryKey(Long id);

	int insertSelective(FeedComment record);

	Long insert(FeedComment record);

	int updateByPrimaryKey(FeedComment record);

	int updateByPrimaryKeySelective(FeedComment record);

	int deleteByPrimaryKey(Long id);

	List<FeedComment> selectByListPage(FeedSearchVo searchVo, int pageNo, int pageSize);

	int totalByFid(FeedSearchVo searchVo);

	List<HashMap> totalByFids(FeedSearchVo searchVo);

	int deleteBySearchVo(FeedSearchVo searchVo);

	List<FeedCommentViewVo> changeToFeedComments(List<FeedComment> feedComments, Long userId);

	List<FeedComment> selectBySearchVo(FeedSearchVo searchVo);

}
