package com.simi.service.feed;

import java.util.HashMap;
import java.util.List;

import com.simi.po.model.feed.FeedComment;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedCommentService {

	FeedComment initFeedComment();

	FeedComment selectByPrimaryKey(Long id);

	List<FeedCommentViewVo> changeToFeedComments(List<FeedComment> cardComments);

	int totalByFid(Long fid);

	List<HashMap> totalByFids(List<Long> fids);

	int insertSelective(FeedComment record);

	int insert(FeedComment record);

	int updateByPrimaryKey(FeedComment record);

	int updateByPrimaryKeySelective(FeedComment record);

	int deleteByPrimaryKey(Long id);

	int deleteByFid(Long fid);

	List<FeedComment> selectByListPage(FeedSearchVo searchVo, int pageNo, int pageSize);

}
