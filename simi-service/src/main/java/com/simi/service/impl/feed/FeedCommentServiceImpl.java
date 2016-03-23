package com.simi.service.impl.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedCommentService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedCommentMapper;

@Service
public class FeedCommentServiceImpl implements FeedCommentService {
	@Autowired
	FeedCommentMapper feedCommentMapper;
	
	@Autowired
	UsersService usersService;	
	
	@Override
	public FeedComment initFeedComment() {
		FeedComment record = new FeedComment();
		record.setId(0L);
		record.setFid(0L);
		record.setUserId(0L);
		record.setComment("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public FeedComment selectByPrimaryKey(Long id) {
		return feedCommentMapper.selectByPrimaryKey(id);
	}
		
	@Override
	public List<FeedComment> selectByListPage(FeedSearchVo searchVo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<FeedComment> list = feedCommentMapper.selectByListPage(searchVo);

		return list;
	}	
	
	@Override
	public List<FeedCommentViewVo> changeToFeedComments(List<FeedComment> feedComments) {
		
		List<FeedCommentViewVo> result = new ArrayList<FeedCommentViewVo>();
		
		if (feedComments.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		for(FeedComment item : feedComments) {
			if (!userIds.contains(item.getUserId()))
			userIds.add(item.getUserId());
		}
		
		List<Users> users = new ArrayList<Users>();
		if (!userIds.isEmpty()) {
			UserSearchVo searchVo = new UserSearchVo();
			searchVo.setUserIds(userIds);
			users = usersService.selectBySearchVo(searchVo);
		}
		
		FeedComment item = null;
		for (int i =0; i < feedComments.size(); i++) {
			item = feedComments.get(i);
			FeedCommentViewVo vo = new FeedCommentViewVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			for (Users u : users) {
				if (u.getId().equals(vo.getUserId())) {
					vo.setName(u.getName());
					vo.setHeadImg(usersService.getHeadImg(u));
					break;
				}
			}
			
			//评论添加时间字符串
			Date addTimeDate = TimeStampUtil.timeStampToDateFull(vo.getAddTime() * 1000, null);
			String addTimeStr = DateUtil.fromToday(addTimeDate);
			vo.setAddTimeStr(addTimeStr);
			
			result.add(vo);
		}
		
		return result;
	}		
	
	@Override
	public int totalByFid(Long fid) {
		return feedCommentMapper.totalByFid(fid);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> totalByFids(List<Long> fids) {
		return feedCommentMapper.totalByFids(fids);
	}	

	@Override
	public int insertSelective(FeedComment record) {
		return feedCommentMapper.insertSelective(record);
	}	
	
	@Override
	public int insert(FeedComment record) {
		return feedCommentMapper.insert(record);
	}	
	
	@Override
	public int updateByPrimaryKey(FeedComment record) {
		return feedCommentMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FeedComment record) {
		return feedCommentMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return feedCommentMapper.deleteByPrimaryKey(id);
	}	
	
	@Override
	public int deleteByFid(Long fid) {
		return feedCommentMapper.deleteByFid(fid);
	}	

}