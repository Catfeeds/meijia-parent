package com.simi.service.impl.feed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedImgsService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.vo.feed.FeedListVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedViewVo;
import com.simi.vo.feed.FeedZanViewVo;
import com.simi.vo.user.UserSearchVo;
import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Users;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.feed.FeedsMapper;

@Service
public class FeedServiceImpl implements FeedService {
	@Autowired
	FeedsMapper feedsMapper;
	
	@Autowired
	FeedImgsService feedImgsService;
	
	@Autowired
	UsersService usersService;

	@Autowired
	FeedZanService feedZanService;
	
	@Autowired
	FeedCommentService feedCommentService;	
	
	@Override
	public Feeds initFeeds() {
		Feeds record = new Feeds();
		record.setFid(0L);
		record.setUserId(0L);
		record.setTitle("");
		record.setLat("");
		record.setLng("");
		record.setPoiName("");
		record.setTotalView(0);
		record.setFeedExtra("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		
		return record;
	}
		
	/**
	 * 转换card 对象为 cardViewVo对象
	 * @param card
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Override
	public FeedViewVo changeToFeedViewVo(Feeds feed) {
		FeedViewVo vo = new FeedViewVo();
		if (feed == null) return vo;
		Long fid = feed.getFid();
		
		//进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(feed, vo);
		
		//获取用户名称
		Users u = usersService.selectByPrimaryKey(vo.getUserId());

		if (u != null) {
			vo.setName(u.getName());
			vo.setHeadImg(usersService.getHeadImg(u));
		}
		
		//统计赞的数量
		int totalZan = feedZanService.totalByFid(fid);
		vo.setTotalZan(totalZan);
		
		//统计评论的数量
		int totalComment = feedCommentService.totalByFid(fid);
		vo.setTotalComment(totalComment);
		
		vo.setTotalView(feed.getTotalView());
		
		//获得点赞前十个用户及头像.
		List<FeedZanViewVo> zanTop10 = feedZanService.getByTop10(fid);
		vo.setZanTop10(zanTop10);
				
		//动态添加时间字符串
		Date addTimeDate = TimeStampUtil.timeStampToDateFull(feed.getAddTime() * 1000, null);
		String addTimeStr = DateUtil.fromToday(addTimeDate);
		vo.setAddTimeStr(addTimeStr);
		
		//卡片图片
		vo.setFeedImgs(new ArrayList<FeedImgs>());
		List<FeedImgs> list = feedImgsService.selectByFid(vo.getFid());

		if (list != null) {
			vo.setFeedImgs(list);
		}
		
		vo.setFeedExtra(feed.getFeedExtra());
		
		return vo;
	}
	
	/**
	 * 批量转换card 对象为 cardViewVo对象
	 * @param List<card>
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<FeedListVo> changeToFeedListVo(List<Feeds> feeds) {
		List<FeedListVo> result = new ArrayList<FeedListVo>();
		if (feeds.isEmpty()) return result;
		
		List<Long> userIds = new ArrayList<Long>();
		List<Long> fids = new ArrayList<Long>();
		for (Feeds item : feeds) {
			if (!fids.contains(item.getFid())) {
				fids.add(item.getFid());
			}
			
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}

		List<Users> userList = new ArrayList<Users>();
		List<HashMap> totalCardZans = new ArrayList<HashMap>();
		List<HashMap> totalCardComments = new ArrayList<HashMap>();
		
		if (!fids.isEmpty()) {
			
			totalCardZans = feedZanService.totalByFids(fids);
			totalCardComments = feedCommentService.totalByFids(fids);
			UserSearchVo searchVo = new UserSearchVo();
			searchVo.setUserIds(userIds);
			userList = usersService.selectBySearchVo(searchVo);
		}
				
		Feeds item = null;
		for (int i = 0; i < fids.size(); i++) {
			item = feeds.get(i);
			FeedListVo vo = new FeedListVo();
			vo.setFid(item.getFid());
			vo.setTitle(item.getTitle());
			vo.setUserId(item.getUserId());
			
			for (Users u : userList) {
				if (u.getId().equals(item.getUserId())) {
					vo.setName(u.getName());
					vo.setHeadImg(usersService.getHeadImg(u));
				}
			}

			//服务时间字符串
			Date addTimeDate = TimeStampUtil.timeStampToDateFull(item.getAddTime() * 1000, null);
			String addTimeStr = DateUtil.fromToday(addTimeDate);
			vo.setAddTimeStr(addTimeStr);			
			
			//统计赞的数量
			vo.setTotalZan(0);
			for (HashMap totalCardZan : totalCardZans) {
				Long tmpFid = Long.valueOf(totalCardZan.get("fid").toString());
				if (tmpFid.equals(vo.getFid())) {
					vo.setTotalZan(Integer.parseInt(totalCardZan.get("total").toString()));
					break;
				}
			}
			
			//统计评论的数量
			vo.setTotalComment(0);
			for (HashMap totalCardComment : totalCardComments) {
				Long tmpCardId = Long.valueOf(totalCardComment.get("fid").toString());
				if (tmpCardId.equals(vo.getFid())) {
					vo.setTotalComment(Integer.parseInt(totalCardComment.get("total").toString()));
					break;
				}
			}
			
			vo.setTotalView(vo.getTotalView());
			
			//卡片图片
			vo.setFeedImgs(new ArrayList<FeedImgs>());
			List<FeedImgs> list = feedImgsService.selectByFid(vo.getFid());

			if (list != null) {
				vo.setFeedImgs(list);
			}

			result.add(vo);
		}
		
		return result;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return feedsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Feeds record) {
		return feedsMapper.insert(record);
	}
	
	@Override
	public Long insertSelective(Feeds record) {
		return feedsMapper.insertSelective(record);
	}

	@Override
	public Feeds selectByPrimaryKey(Long id) {
		return feedsMapper.selectByPrimaryKey(id);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageInfo selectByListPage(FeedSearchVo vo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<Feeds> list = new ArrayList<Feeds>();
			
		list = feedsMapper.selectByListPage(vo);
		
		PageInfo result = new PageInfo(list);
		return result;
	}	

	@Override
	public int updateByPrimaryKey(Feeds record) {
		return feedsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Feeds record) {
		return feedsMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int updateByTotalView(Long fid) {
		return feedsMapper.updateByTotalView(fid);
	}
		
}