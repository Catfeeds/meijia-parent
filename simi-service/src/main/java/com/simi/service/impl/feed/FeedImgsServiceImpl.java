package com.simi.service.impl.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedImgsMapper;
import com.simi.po.model.feed.FeedImgs;
import com.simi.service.feed.FeedImgsService;

@Service
public class FeedImgsServiceImpl implements FeedImgsService {
	@Autowired
	FeedImgsMapper feedImgsMapper;
	
	@Override
	public FeedImgs initFeedImgs() {
		FeedImgs record = new FeedImgs();
		record.setId(0L);
		record.setFid(0L);
		record.setUserId(0L);
		record.setImgUrl("");
		record.setImgMiddle("");
		record.setImgSmall("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public int insert(FeedImgs record) {
	
		return feedImgsMapper.insert(record);
	}
	
	@Override
	public int deleteByFid(Long fid) {
		return feedImgsMapper.deleteByFid(fid);
	}	
	
	@Override
	public List<FeedImgs> selectByFid(Long fid) {
		return feedImgsMapper.selectByFid(fid);
	}
	
	
}