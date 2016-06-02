package com.simi.po.dao.feed;

import java.util.List;

import com.simi.po.model.feed.Feeds;
import com.simi.vo.feed.FeedSearchVo;

public interface FeedsMapper {
    int deleteByPrimaryKey(Long fid);

    Long insert(Feeds record);

    Long insertSelective(Feeds record);

    Feeds selectByPrimaryKey(Long fid);

    int updateByPrimaryKeySelective(Feeds record);

    int updateByPrimaryKey(Feeds record);

	List<Feeds> selectByListPage(FeedSearchVo vo);
	
	List<Feeds> selectBySearchVo(FeedSearchVo vo);

	int updateByTotalView(Long fid);
}