package com.simi.service.impl.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.feed.FeedCommentService;
import com.simi.service.feed.FeedImgsService;
import com.simi.service.feed.FeedService;
import com.simi.service.feed.FeedTagsService;
import com.simi.service.feed.FeedZanService;
import com.simi.service.user.UsersService;
import com.simi.utils.FeedUtil;
import com.simi.vo.TagVo;
import com.simi.vo.feed.FeedListVo;
import com.simi.vo.feed.FeedSearchVo;
import com.simi.vo.feed.FeedVo;
import com.simi.po.model.feed.FeedImgs;
import com.simi.po.model.feed.FeedTags;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.Users;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
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

	@Autowired
	FeedTagsService feedTagsService;

	@Override
	public Feeds initFeeds() {
		Feeds record = new Feeds();
		record.setFid(0L);
		record.setUserId(0L);
		record.setFeedType((short) 0);
		record.setTitle("");
		record.setLat("");
		record.setLng("");
		record.setPoiName("");
		record.setTotalView(0);
		record.setFeedExtra("");
		record.setStatus((short) 0);
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}

	@Override
	public FeedListVo changeToFeedListVo(Feeds item) {

		Long fid = item.getFid();
		FeedListVo vo = new FeedListVo();

		// 进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		// 获取用户名称
		Users u = usersService.selectByPrimaryKey(vo.getUserId());

		if (u != null) {
			vo.setName(u.getName());
			vo.setHeadImg(usersService.getHeadImg(u));
			
			if (StringUtil.isEmpty(vo.getName())) {
				vo.setName(u.getMobile());
			}
			
		}

		// 服务时间字符串
		Date addTimeDate = TimeStampUtil.timeStampToDateFull(item.getAddTime() * 1000, null);
		String addTimeStr = DateUtil.fromToday(addTimeDate);
		vo.setAddTimeStr(addTimeStr);

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFid(fid);
		searchVo.setFeedType(item.getFeedType());
		// 统计赞的数量
		int totalZan = feedZanService.totalByFid(searchVo);
		vo.setTotalZan(totalZan);

		// 统计评论的数量
		int totalComment = feedCommentService.totalByFid(searchVo);
		vo.setTotalComment(totalComment);

		// 动态图片
		vo.setFeedImgs(new ArrayList<FeedImgs>());
		List<FeedImgs> list = feedImgsService.selectBySearchVo(searchVo);

		if (list != null) {
			vo.setFeedImgs(list);
		}

		// 动态标签/类别
		List<TagVo> feedTagVos = new ArrayList<TagVo>();
		List<FeedTags> feedTags = feedTagsService.selectBySearchVo(searchVo);
		for (FeedTags item1 : feedTags) {
			TagVo tvo = new TagVo();
			tvo.setTagId(item1.getTagId());
			tvo.setTagName(item1.getTags());
			feedTagVos.add(tvo);
		}

		vo.setFeedTags(feedTagVos);
		return vo;
	}
	
	@Override
	public FeedVo changeToFeedVo(Feeds item) {

		Long fid = item.getFid();
		FeedVo vo = new FeedVo();

		// 进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);

		// 获取用户名称
		Users u = usersService.selectByPrimaryKey(vo.getUserId());

		if (u != null) {
			vo.setName(u.getName());
			vo.setHeadImg(usersService.getHeadImg(u));
			
			if (StringUtil.isEmpty(vo.getName())) {
				vo.setName(MobileUtil.getMobileStar(u.getMobile()));
			}
			
		}

		// 服务时间字符串
		Date addTimeDate = TimeStampUtil.timeStampToDateFull(item.getAddTime() * 1000, null);
		String addTimeStr = DateUtil.fromToday(addTimeDate);
		vo.setAddTimeStr(addTimeStr);

		FeedSearchVo searchVo = new FeedSearchVo();
		searchVo.setFid(fid);
		searchVo.setFeedType(item.getFeedType());
		// 统计赞的数量
		int totalZan = feedZanService.totalByFid(searchVo);
		vo.setTotalZan(totalZan);

		// 统计评论的数量
		int totalComment = feedCommentService.totalByFid(searchVo);
		vo.setTotalComment(totalComment);

		// 动态图片
		vo.setFeedImgs(new ArrayList<FeedImgs>());
		List<FeedImgs> list = feedImgsService.selectBySearchVo(searchVo);

		if (list != null) {
			vo.setFeedImgs(list);
		}

		// 动态标签/类别
		List<TagVo> feedTagVos = new ArrayList<TagVo>();
		List<FeedTags> feedTags = feedTagsService.selectBySearchVo(searchVo);
		for (FeedTags item1 : feedTags) {
			TagVo tvo = new TagVo();
			tvo.setTagId(item1.getTagId());
			tvo.setTagName(item1.getTags());
			feedTagVos.add(tvo);
		}
		
		//状态名称
		String statusName = FeedUtil.getFeedStatusName(vo.getStatus());
		vo.setStatusName(statusName);

		vo.setFeedTags(feedTagVos);
		return vo;
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
	
	@Override
	public List<HashMap> totalByUserIds(FeedSearchVo searchVo) {
		return feedsMapper.totalByUserIds(searchVo);
	}

}