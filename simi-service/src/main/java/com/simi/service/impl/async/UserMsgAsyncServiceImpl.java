package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.feed.FeedService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;

@Service
public class UserMsgAsyncServiceImpl implements UserMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;	
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardAttendService cardAttendService;	
	
	@Autowired
	private FeedService feedService;	

	/**
	 * 新增用户时发送默认消息
	 */
	@Async
	@Override
	public Future<Boolean> newUserMsg(Long userId) {

		Users defaultUser = usersService.selectByMobile("18888888888");
		UserMsg record = userMsgService.initUserMsg();
		
		record.setUserId(userId);
		record.setFromUserId(defaultUser.getId());
		record.setCategory("h5");
		record.setAction("newuser");
		record.setParams("");
		record.setGotoUrl("http://eqxiu.com/s/5RaYy1wM");
		record.setTitle("云行政小秘");
		record.setSummary("欢迎来到云行政，新手必读，立刻体验");
		userMsgService.insert(record);
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 新增卡片时发送消息
	 */
	@Async
	@Override
	public Future<Boolean> newCardMsg(Long cardId) {
		
		Cards card = cardService.selectByPrimaryKey(cardId);
		if (card == null) return new AsyncResult<Boolean>(true);
		
		Long createUserId = card.getCreateUserId();
		//找出所有需要发送消息人员:
		//获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		List<Long> userIds = new ArrayList<Long>();
		
		for(CardAttend item : attends) {
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}
		
		if (!userIds.contains(createUserId)) userIds.add(createUserId);
		
		String serviceContent = card.getServiceContent();
		if (serviceContent!= null && serviceContent.length() > 20) {
			serviceContent = serviceContent.substring(0, 20);
		}		
		
		if (serviceContent.length() == 0) {
			serviceContent = "你创建了新的日程安排";
		}
		
		for(Long uid : userIds) {
			UserMsg record = userMsgService.initUserMsg();
			
			record.setUserId(uid);
			record.setFromUserId(createUserId);
			record.setCategory("app");
			record.setAction("card");
			record.setParams(cardId.toString());
			record.setGotoUrl("");
			record.setTitle(CardUtil.getCardTypeName(card.getCardType()));
			

			record.setSummary(serviceContent);
			userMsgService.insert(record);			
		}
		
		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 新增动态时发送消息
	 */
	@Async
	@Override
	public Future<Boolean> newFeedMsg(Long fid) {
		
		Feeds feed = feedService.selectByPrimaryKey(fid);
		if (feed == null) return new AsyncResult<Boolean>(true);
		
		Long userId = feed.getFid();
		UserMsg record = userMsgService.initUserMsg();
		
		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setCategory("app");
		record.setAction("feed");
		record.setParams(fid.toString());
		record.setGotoUrl("");
		record.setTitle("发布动态");
		
		String title = feed.getTitle();
		
		if (title!= null && title.length() > 20) {
			title = title.substring(0, 20);
		}		
		
		if (title.length() == 0) {
			title = "你发布了新的动态";
		}
		
		record.setSummary(title);
		
		userMsgService.insert(record);				
		
		return new AsyncResult<Boolean>(true);
	}		

}
