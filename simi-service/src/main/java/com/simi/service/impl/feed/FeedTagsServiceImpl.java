package com.simi.service.impl.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedTagsService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.po.model.feed.FeedTags;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedTagsMapper;

@Service
public class FeedTagsServiceImpl implements FeedTagsService {
	@Autowired
	FeedTagsMapper feedTagsMapper;

	@Autowired
	UsersService usersService;

	@Override
	public FeedTags initFeedTags() {
		FeedTags record = new FeedTags();
		record.setId(0L);
		record.setFid(0L);
		record.setUserId(0L);
		record.setFeedType((short) 0);
		record.setTagId(0L);
		record.setTags("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public FeedTags selectByPrimaryKey(Long id) {
		return feedTagsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<FeedTags> selectBySearchVo(FeedSearchVo searchVo) {
		return feedTagsMapper.selectBySearchVo(searchVo);
	}	

	@Override
	public int insertSelective(FeedTags record) {
		return feedTagsMapper.insertSelective(record);
	}
	
	@Override
	public int insert(FeedTags record) {
		return feedTagsMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(FeedTags record) {
		return feedTagsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FeedTags record) {
		return feedTagsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return feedTagsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteBySearchVo(FeedSearchVo searchVo) {
		return feedTagsMapper.deleteBySearchVo(searchVo);
	}

}