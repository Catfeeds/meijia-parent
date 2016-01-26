package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
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
import com.simi.vo.UserMsgSearchVo;

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
		record.setToUserId(userId);
		record.setCategory("h5");
		record.setAction("newuser");
		record.setParams("");
		record.setGotoUrl("http://eqxiu.com/s/5RaYy1wM");
		record.setTitle("云行政小秘");
		record.setSummary("欢迎来到云行政，新手必读，立刻体验");
		record.setIconUrl("http://123.57.173.36/images/icon/icon-tixing.png");
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
			record.setToUserId(uid);
			record.setCategory("app");
			record.setAction("card");
			record.setParams(cardId.toString());
			record.setGotoUrl("");
			record.setTitle(CardUtil.getCardTypeName(card.getCardType()));

			record.setSummary(serviceContent);
			record.setIconUrl(CardUtil.getCardIcon(card.getCardType()));
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
		record.setToUserId(userId);
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
		record.setIconUrl("http://123.57.173.36/images/icon/icon-baogao.png");
		userMsgService.insert(record);				
		
		return new AsyncResult<Boolean>(true);
	}		
	
//	@Async
	@Override
	public Future<Boolean> newImMsg(Long 	fromUserId, 
									String 	fromUserName, 
									Long 	toUserId, 
									String 	toUserName, 
									String 	imgContent) {	
		
		//需要分别往发送者和接收者都存储。
		Users fromUser = usersService.selectByPrimaryKey(fromUserId);
		Users toUser = usersService.selectByPrimaryKey(toUserId);
		
		//1. 往发送者存储消息
		UserMsgSearchVo fromSearchVo = new UserMsgSearchVo();
		fromSearchVo.setFromUserId(fromUserId);
		fromSearchVo.setToUserId(toUserId);
		fromSearchVo.setCategory("app");
		fromSearchVo.setAction("im");
		fromSearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		fromSearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<UserMsg> fromList = userMsgService.selectBySearchVo(fromSearchVo);
		
		UserMsg fromMsg = userMsgService.initUserMsg();
		if (!fromList.isEmpty()) {
			fromMsg = fromList.get(0);
		}
		
		fromMsg.setUserId(fromUserId);
		fromMsg.setFromUserId(fromUserId);
		fromMsg.setToUserId(toUserId);
		fromMsg.setCategory("app");
		fromMsg.setAction("im");
		fromMsg.setParams(toUserName);
		fromMsg.setGotoUrl("");
		fromMsg.setTitle(toUser.getName());		
		fromMsg.setSummary(imgContent);
		fromMsg.setIconUrl(toUser.getHeadImg());
		if (fromMsg.getMsgId() > 0L) {
			fromMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(fromMsg);
		} else {
			userMsgService.insert(fromMsg);
		}
		
		//2. 往接收者存储消息
		UserMsgSearchVo toSearchVo = new UserMsgSearchVo();
		toSearchVo.setFromUserId(toUserId);
		toSearchVo.setToUserId(fromUserId);
		toSearchVo.setCategory("app");
		toSearchVo.setAction("im");
		toSearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		toSearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<UserMsg> toList = userMsgService.selectBySearchVo(toSearchVo);
		
		UserMsg toMsg = userMsgService.initUserMsg();
		if (!toList.isEmpty()) {
			toMsg = toList.get(0);
		}
		
		toMsg.setUserId(toUserId);
		toMsg.setFromUserId(fromUserId);
		toMsg.setToUserId(fromUserId);
		toMsg.setCategory("app");
		toMsg.setAction("im");
		toMsg.setParams(fromUserName);
		toMsg.setGotoUrl("");
		toMsg.setTitle(fromUser.getName());		
		toMsg.setSummary(imgContent);
		toMsg.setIconUrl(fromUser.getHeadImg());
		if (toMsg.getMsgId() > 0L) {
			toMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(toMsg);
		} else {
			userMsgService.insert(toMsg);
		}		
		
		return new AsyncResult<Boolean>(true);
	}

}
