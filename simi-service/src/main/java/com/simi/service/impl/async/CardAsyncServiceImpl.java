package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.GsonUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.async.CardAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.dict.CityService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;

@Service
public class CardAsyncServiceImpl implements CardAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardLogService cardLogService;	
	
	@Autowired
	CardAttendService cardAttendService;	
	
	@Autowired
	private UserPushBindService userPushBindService;	
	
	@Autowired
	private CityService cityService;	
	
	/**
	 * 第三方登录，注册绑定环信账号,异步操作
	 */
	@Async
	@Override
	public Future<Boolean> cardLog(Long userId, Long cardId, String remarks) {
		
		
		Users createUser = usersService.selectByPrimaryKey(userId);
		
		Cards card = cardService.selectByPrimaryKey(cardId);
		
		CardLog cardLog = cardLogService.initCardLog();
		cardLog.setCardId(cardId);
		cardLog.setUserId(userId);
		
		String userName = createUser.getName().equals("") ? createUser.getMobile() : createUser.getName();
		cardLog.setUserName(userName);
		cardLog.setStatus(card.getStatus());		
		cardLog.setRemarks(remarks);
		
		cardLogService.insert(cardLog);		
		
		return new AsyncResult<Boolean>(true);
	}	
	
	/**
	 * 卡片发送推送消息和短信消息.
	 * @param card 卡片对象
	 * @param pushToApp  是否给app发送消息
	 * @param pushToSms  是否发送sms短信消息
	 * @param pushToIos  是否给app发送离线消息，此场景应用为当ios app被杀死的情况下，ios无法设定闹钟，则进行后台推送提醒。
	 */
	@Async
	@Override
	public Future<Boolean> cardNotification(Cards card) {
		
		//1找出所有需要通知的用户集合 users
		Long cardId = card.getCardId();
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		
		if (attends.isEmpty()) return new AsyncResult<Boolean>(true);
		
		List<Long> userIds = new ArrayList<Long>();
		for (CardAttend item : attends) {
			if (!userIds.equals(item.getUserId())) userIds.add(item.getUserId());
		}
				
		//2.找出可以发推送消息的用户集合 userPushBinds
		List<UserPushBind> userPushBinds = userPushBindService.selectByUserIds(userIds);
		List<Long> userPushIds = new ArrayList<Long>();
		if (!userPushBinds.isEmpty()) {
			for (UserPushBind p : userPushBinds) {
				if (!userPushIds.contains(p.getUserId())) {
					userPushIds.add(p.getUserId());
				}
			}
			//进行消息推送.
			pushToApp(card, userPushBinds);
		}
		
		//如果不需要立即推送，则不需要发送短信了
		if (card.getSetNowSend().equals((short)0)) {
			return new AsyncResult<Boolean>(true);
		}
		
		
		//3.根据集合users 和集合userPushBinds，找出不能推送，只能发短信的用户集合C
		List<Long> userSmsIds = new ArrayList<Long>();
		userSmsIds  = userIds;
		if (!userPushBinds.isEmpty()) {
			userSmsIds.removeAll(userPushIds);
		} 
		
		if (!userSmsIds.isEmpty() && card.getSetNowSend().equals((short)1)) {
			//进行短信发送
			pushToSms(card, userSmsIds);
		}
		
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 卡片发送推送消息和短信消息.
	 * @param card 卡片对象
  	 * 给ios app发送离线消息，此场景应用为当ios app被杀死的情况下，ios无法设定闹钟，则进行后台推送提醒。
	 */
	@Async
	@Override
	public Future<Boolean> cardAlertClock(Cards card) {
		
		//1找出所有需要通知的用户集合 users
		Long cardId = card.getCardId();
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		
		if (attends.isEmpty()) return new AsyncResult<Boolean>(true);
		
		List<Long> userIds = new ArrayList<Long>();
		for (CardAttend item : attends) {
			if (!userIds.equals(item.getUserId())) userIds.add(item.getUserId());
		}
				
		//2.找出可以发推送消息的用户集合 userPushBinds
		List<UserPushBind> userPushBinds = userPushBindService.selectByUserIds(userIds);
		List<Long> userPushIds = new ArrayList<Long>();
		if (!userPushBinds.isEmpty()) {
			for (UserPushBind p : userPushBinds) {
				if (!userPushIds.contains(p.getUserId())) {
					userPushIds.add(p.getUserId());
				}
			}
			
			//进行闹钟消息推送.
			pushToAlarm(card, userPushBinds);
		}
		
		//3.根据集合users 和集合userPushBinds，找出不能推送，只能发短信的用户集合C
		List<Long> userSmsIds = new ArrayList<Long>();
		userSmsIds  = userIds;
		if (!userPushBinds.isEmpty()) {
			userSmsIds.removeAll(userPushIds);
		} 
		
		if (!userSmsIds.isEmpty() && card.getSetNowSend().equals((short)1)) {
			//进行短信发送
			pushToSms(card, userSmsIds);
		}
		
		return new AsyncResult<Boolean>(true);
	}	
	
	private boolean pushToApp(Cards card, List<UserPushBind> userPushBinds) {
		
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		HashMap<String, String> tranParams = new HashMap<String, String>();

		Short cardType = card.getCardType();
		Long serviceTime = card.getServiceTime();
		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String pushContent = "";
		if (!StringUtil.isEmpty(card.getServiceContent())) {
			pushContent = pushContent.substring(0, 20);
		}
		
		//获得提醒时间
		Short setRemind = card.getSetRemind();
		int remindMin = CardUtil.getRemindMin(setRemind);
		
		Long remindTime = serviceTime - remindMin * 60;
		
		serviceTime = serviceTime * 1000;
		remindTime = remindTime * 1000;
		
		String isShow = "true";
		
		if (card.getSetNowSend().equals((short)0)) isShow = "false";
		if (card.getSetNowSend().equals((short)1)) isShow = "true";
		
		tranParams.put("is_show", isShow);
		tranParams.put("action", "setclock");
		tranParams.put("card_id", card.getCardId().toString());
		tranParams.put("card_type", card.getCardType().toString());
		tranParams.put("service_time", serviceTime.toString());
		tranParams.put("remind_time", remindTime.toString());
		tranParams.put("remind_title", cardTypeName);
		tranParams.put("remind_content", pushContent);


		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonParams = "";
		try {
			jsonParams = objectMapper.writeValueAsString(tranParams);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		for (UserPushBind p : userPushBinds) {
						
			params.put("transmissionContent", jsonParams);
			params.put("cid", p.getClientId());
			
			if (p.getDeviceType().equals("ios")) {
				try {
					PushUtil.IOSPushToSingle(params, "notification");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (p.getDeviceType().equals("android")) {
				try {
					PushUtil.AndroidPushToSingle(params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return true;
	}
	
	/**
	 * 因为app后台进程被杀死的情况下，无法设定闹钟，所以必须后台定时提醒
	 * 1. 后台做相应检测，如果为离线状态则需要发送
	 * @param card
	 * @param userPushBinds
	 * @return
	 */
	private boolean pushToAlarm(Cards card, List<UserPushBind> userPushBinds) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		HashMap<String, String> tranParams = new HashMap<String, String>();

		Short cardType = card.getCardType();
		Long serviceTime = card.getServiceTime();
		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String pushContent = "";
		if (!StringUtil.isEmpty(card.getServiceContent())) {
			pushContent = card.getServiceContent().substring(0, 20);	
		}
		
		//获得提醒时间
		Short setRemind = card.getSetRemind();
		int remindMin = CardUtil.getRemindMin(setRemind);
		
		Long remindTime = serviceTime - remindMin * 60;
		
		serviceTime = serviceTime * 1000;
		remindTime = remindTime * 1000;
		
		String isShow = "true";		
		tranParams.put("is_show", isShow);
		tranParams.put("action", "alarm");
		tranParams.put("card_id", card.getCardId().toString());
		tranParams.put("card_type", card.getCardType().toString());
		tranParams.put("service_time", serviceTime.toString());
		tranParams.put("remind_time", remindTime.toString());
		tranParams.put("remind_title", cardTypeName);
		tranParams.put("remind_content", pushContent);


		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonParams = "";
		try {
			jsonParams = objectMapper.writeValueAsString(tranParams);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		for (UserPushBind p : userPushBinds) {
						
			params.put("transmissionContent", jsonParams);
			params.put("cid", p.getClientId());
			
			String userStatus = PushUtil.getUserStatus(p.getClientId());
			if (p.getDeviceType().equals("ios")) {
				try {
					if (userStatus.equals("Offline")) {
						PushUtil.IOSPushToSingle(params, "alertClock");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (p.getDeviceType().equals("android")) {
				try {
					if (userStatus.equals("Offline")) {
						PushUtil.AndroidPushToSingle(params);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return true;
	}	
	
	
	private boolean pushToSms(Cards card, List<Long> userSmsIds) {
		
		List<Users> userSms = new ArrayList<Users>();
		
		if (userSmsIds.isEmpty()) return true;
		userSms = usersService.selectByUserIds(userSmsIds);
		
		Users createUsers = usersService.selectByPrimaryKey(card.getCreateUserId());
		
		Users user = usersService.selectByPrimaryKey(card.getUserId());
		
		String createUserName = createUsers.getName();
		
		String createUserMobile = createUsers.getMobile();
		
		if (createUserName.equals(createUserMobile)) {
			createUserName = "";
		} else {
			createUserName = createUserName + " ";
		}
		
		Long serviceTime = card.getServiceTime();
		String serviceTimeStr = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "yyyy-MM-dd HH:mm");
		String serviceAddr = card.getServiceAddr();
		String serviceContent = card.getServiceContent();
		
		if (serviceContent.length() > 20) {
			serviceContent = serviceContent.substring(0, 20) + "...";
		}
		
		String fromCityName = "";
		String toCityName = "";
		
		if (card.getCardType().equals((short)5)) {
			String cardExtra = card.getCardExtra();
			Map<String, Object> cardExtraMap = GsonUtil.GsonToMaps(cardExtra);
			fromCityName = cardExtraMap.get("ticket_from_city_name").toString();
			toCityName = cardExtraMap.get("ticket_to_city_name").toString();
		}
		
		String[] content;
		String mobile = "";
		HashMap<String, String> sendSmsResult;
		//开始发送短信
		for (Users item : userSms) {
			if (StringUtil.isEmpty(item.getMobile())) continue;
			mobile = item.getMobile();
			switch (card.getCardType()) {
			case 0:
//				statusName = "通用";
				break;
			case 1:
//				statusName = "会议安排";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceAddr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44665", content);
				
				break;
			case 2:
//				statusName = "通知公告";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44668", content);				
				break;
			case 3:
//				statusName = "事务提醒";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44666", content);						
				break;
			case 4:
//				statusName = "面试邀约";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44667", content);		
				break;
			case 5:
//				statusName = "差旅规划";
				content = new String[] { createUserName, createUserMobile,  fromCityName, toCityName, serviceTimeStr};
				sendSmsResult = SmsUtil.SendSms(mobile, "44664", content);		
				break;
			default:
//				statusName = "";
			}					
			
			
		}
		
		return true;
	}	
		
}
