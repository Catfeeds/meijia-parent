package com.simi.service.impl.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedCommentViewVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.po.model.feed.FeedComment;
import com.simi.po.model.feed.FeedZan;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedCommentMapper;

@Service
public class FeedCommentServiceImpl implements FeedCommentService {
	@Autowired
	FeedCommentMapper feedCommentMapper;
	
	@Autowired
	UsersService usersService;	
	
	@Autowired
	FeedZanService feedZanService;
	
	@Override
	public FeedComment initFeedComment() {
		FeedComment record = new FeedComment();
		record.setId(0L);
		record.setFid(0L);
		record.setUserId(0L);
		record.setFeedType((short) 0);
		record.setComment("");
		record.setStatus((short) 0);
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
	public List<FeedCommentViewVo> changeToFeedComments(List<FeedComment> feedComments, Long userId) {
		
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
		
		
		FeedSearchVo searchVo = new FeedSearchVo();

		
		for (int i =0; i < feedComments.size(); i++) {
			item = feedComments.get(i);
			FeedCommentViewVo vo = new FeedCommentViewVo();
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			for (Users u : users) {
				if (u.getId().equals(vo.getUserId())) {
					vo.setName(u.getName());
					vo.setHeadImg(usersService.getHeadImg(u));
					
					if (StringUtil.isEmpty(vo.getName())) {
						vo.setName(MobileUtil.getMobileStar(u.getMobile()));
					}
					
					break;
				}
			}
			
			//评论添加时间字符串
			Date addTimeDate = TimeStampUtil.timeStampToDateFull(vo.getAddTime() * 1000, null);
			String addTimeStr = DateUtil.fromToday(addTimeDate);
			vo.setAddTimeStr(addTimeStr);

			// 统计赞的数量
			searchVo = new FeedSearchVo();
			searchVo.setFid(vo.getFid());
			searchVo.setFeedType(vo.getFeedType());		
			searchVo.setCommentId(vo.getId());
			int totalZan = feedZanService.totalByFid(searchVo);
			vo.setTotalZan(totalZan);
			
			//用户是否已经点过赞
			vo.setIsZan((short) 0);
			
			if (userId > 0L) {
				searchVo = new FeedSearchVo();
				searchVo.setFid(vo.getFid());
				searchVo.setFeedType(vo.getFeedType());		
				searchVo.setCommentId(vo.getId());
				searchVo.setUserId(userId);
				List<FeedZan> zans = feedZanService.selectBySearchVo(searchVo);
				
				if (!zans.isEmpty()) vo.setIsZan((short) 1);
			}
			result.add(vo);
		}
		
		return result;
	}		
	
	@Override
	public int totalByFid(FeedSearchVo searchVo) {
		return feedCommentMapper.totalByFid(searchVo);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> totalByFids(FeedSearchVo searchVo) {
		return feedCommentMapper.totalByFids(searchVo);
	}	

	@Override
	public int insertSelective(FeedComment record) {
		return feedCommentMapper.insertSelective(record);
	}	
	
	@Override
	public Long insert(FeedComment record) {
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
	public int deleteBySearchVo(FeedSearchVo searchVo) {
		return feedCommentMapper.deleteBySearchVo(searchVo);
	}	

}