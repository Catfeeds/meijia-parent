package com.simi.service.impl.card;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.dict.CityService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.CardZanViewVo;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.dict.DictCity;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.SmsUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.po.dao.card.CardsMapper;

@Service
public class CardsServiceImpl implements CardService {
	@Autowired
	CardsMapper cardsMapper;
	
	@Autowired
	UsersService usersService;

	@Autowired
	CardAttendService cardAttendService;

	@Autowired
	CardZanService cardZanService;
	
	@Autowired
	CardCommentService cardCommentService;	
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserPushBindService userPushBindService;
	
	@Override
	public Cards initCards() {
		Cards record = new Cards();
		record.setCardId(0L);
		record.setCreateUserId(0L);
		record.setUserId(0L);
		record.setCardType((short) 0);
		record.setServiceTime(0L);
		record.setServiceContent("");
		record.setSetRemind((short) 0);
		record.setSetNowSend((short) 0);
		record.setSetSecDo((short) 0);
		record.setSetSecRemarks("");
		record.setTicketType((short) 0);
		record.setTicketFromCityId(0L);
		record.setTicketToCityId(0L);
		record.setStatus((short) 1);
		record.setSecRemarks("");
		record.setTitle("");
		record.setPoiLat("");
		record.setPoiLng("");
		record.setPoiName("");
		record.setSetFriendView((short) 0);
		
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());

		return record;
	}
	
	/**
	 * 转换card 对象为 cardViewVo对象
	 * @param card
	 * @return
	 */
	@Override
	public CardViewVo changeToCardViewVo(Cards card) {
		CardViewVo vo = new CardViewVo();
		if (card == null) return vo;
		Long cardId = card.getCardId();
		
		//进行对象复制.
		BeanUtilsExp.copyPropertiesIgnoreNull(card, vo);
		
		//获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		vo.setAttends(attends);
		
		//获取用户名称
		Users createUser = usersService.getUserInfo(vo.getCreateUserId());
		if (createUser != null) vo.setCreateUserName(createUser.getName());
		
		Users u = usersService.getUserInfo(vo.getUserId());
		if (u != null) {
			vo.setUserName(u.getName());
			vo.setUserHeadImg(u.getHeadImg());
		}
		
		//统计赞的数量
		int totalZan = cardZanService.totalByCardId(cardId);
		vo.setTotalZan(totalZan);
		
		//统计评论的数量
		int totalComment = cardCommentService.totalByCardId(cardId);
		vo.setTotalComment(totalComment);
		
		//获得点赞前十个用户及头像.
		List<CardZanViewVo> zanTop10 = cardZanService.getByTop10(cardId);
		vo.setZanTop10(zanTop10);
		
		//卡片类型名称
		String cardTypeName = MeijiaUtil.getCardTypeName(vo.getCardType());
		vo.setCardTypeName(cardTypeName);
		
		//卡片添加时间字符串
		Date addTimeDate = TimeStampUtil.timeStampToDateFull(vo.getAddTime() * 1000, null);
		String addTimeStr = DateUtil.fromToday(addTimeDate);
		vo.setAddTimeStr(addTimeStr);
		
		
		vo.setTicketFromCityName("");
		vo.setTicketToCityName("");
		if (vo.getTicketFromCityId() > 0L || vo.getTicketToCityId() > 0L) {
			List<DictCity> cityList  = cityService.selectAll();
			for (DictCity city : cityList) {
				if (city.getCityId().equals(vo.getTicketFromCityId())) {
					vo.setTicketFromCityName(city.getName());
				}
				
				if (city.getCityId().equals(vo.getTicketToCityId())) {
					vo.setTicketToCityName(city.getName());
				}
			}
		}
		
		
		return vo;
	}
	
	/**
	 * 批量转换card 对象为 cardViewVo对象
	 * @param List<card>
	 * @return
	 */
	@Override
	public List<CardViewVo> changeToCardViewVoBat(List<Cards> cards) {
		List<CardViewVo> result = new ArrayList<CardViewVo>();
		if (cards.isEmpty()) return result;
		
		List<Long> cardIds = new ArrayList<Long>();
		List<Long> createUserIds = new ArrayList<Long>();
		List<Long> userIds = new ArrayList<Long>();
		
		for (Cards item : cards) {
			cardIds.add(item.getCardId());
			createUserIds.add(item.getCreateUserId());
			userIds.add(item.getUserId());
		}
		
		List<Users> createUsers = new ArrayList<Users>();
		if (!createUserIds.isEmpty()) {
			createUsers = usersService.selectByUserIds(createUserIds);
		}
		
		List<Users> users = new ArrayList<Users>();
		if (!userIds.isEmpty()) {
			users = usersService.selectByUserIds(userIds);
		}
		
		
		List<CardAttend> cardAttends = new ArrayList<CardAttend>();
		List<HashMap> totalCardZans = new ArrayList<HashMap>();
		List<HashMap> totalCardComments = new ArrayList<HashMap>();
		
		if (!cardIds.isEmpty()) {
			cardAttends = cardAttendService.selectByCardIds(cardIds);
			totalCardZans = cardZanService.totalByCardIds(cardIds);
			totalCardComments = cardCommentService.totalByCardIds(cardIds);
		}
		
		List<DictCity> cityList  = cityService.selectAll();
		
		Cards item = null;
		for (int i = 0; i < cards.size(); i++) {
			item = cards.get(i);
			CardViewVo vo = new CardViewVo();
			//进行对象复制.
			BeanUtilsExp.copyPropertiesIgnoreNull(item, vo);
			
			//获取到参会、参与人员列表
			List<CardAttend> cardAttendItems = new ArrayList<CardAttend>();
			for (CardAttend cardAttend : cardAttends) {
				if (cardAttend.getCardId().equals(vo.getCardId())) {
					cardAttendItems.add(cardAttend);
				}
			}
			vo.setAttends(cardAttendItems);
			
			//获取创建用户名称
			for (Users u : users) {
				if (u.getId().equals(vo.getUserId())) {
					vo.setCreateUserName(u.getName());
					break;
				}
			}
			
			//获取创建用户名称
			for (Users createUser : createUsers) {
				if (createUser.getId().equals(vo.getCreateUserId())) {
					vo.setCreateUserName(createUser.getName());
					break;
				}
			}			
			
			//统计评论的数量
			vo.setTotalComment(0);
			for (HashMap totalCardComment : totalCardComments) {
				Long tmpCardId = Long.valueOf(totalCardComment.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalComment(Integer.parseInt(totalCardComment.get("total").toString()));
				}
			}
			
			//统计赞的数量
			vo.setTotalZan(0);
			for (HashMap totalCardZan : totalCardZans) {
				Long tmpCardId = Long.valueOf(totalCardZan.get("card_id").toString());
				if (tmpCardId.equals(vo.getCardId())) {
					vo.setTotalZan(Integer.parseInt(totalCardZan.get("total").toString()));
				}
			}		
			
			//获得点赞前十个用户及头像.
			vo.setZanTop10(new ArrayList<CardZanViewVo>());
			
			//卡片类型名称
			String cardTypeName = MeijiaUtil.getCardTypeName(vo.getCardType());
			vo.setCardTypeName(cardTypeName);
			
			//服务时间字符串
			Date addTimeDate = TimeStampUtil.timeStampToDateFull(vo.getAddTime() * 1000, null);
			String addTimeStr = DateUtil.fromToday(addTimeDate);
			vo.setAddTimeStr(addTimeStr);
			
			
			//城市名称
			vo.setTicketFromCityName("");
			vo.setTicketToCityName("");
			if (vo.getTicketFromCityId() > 0L || vo.getTicketToCityId() > 0L) {
				for (DictCity city : cityList) {
					if (city.getCityId().equals(vo.getTicketFromCityId())) {
						vo.setTicketFromCityName(city.getName());
					}
					
					if (city.getCityId().equals(vo.getTicketToCityId())) {
						vo.setTicketToCityName(city.getName());
					}
				}
			}
			
			result.add(vo);
		}
		
		return result;
	}	
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return cardsMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Long insert(Cards record) {
		return cardsMapper.insert(record);
	}
	
	@Override
	public int insertSelective(Cards record) {
		return cardsMapper.insertSelective(record);
	}

	@Override
	public Cards selectByPrimaryKey(Long id) {
		return cardsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public PageInfo selectByListPage(CardSearchVo vo, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		
		List<Cards> list = new ArrayList<Cards>();
		
		Short cardFrom = vo.getCardFrom();
		
		//某个用户所有的卡片
		if (cardFrom.equals((short)0)) {
			list = cardsMapper.selectByListPage(vo);
		}
		
		//某个用户发布的卡片
		if (cardFrom.equals((short)1)) {
			list = cardsMapper.selectMineByListPage(vo);
		}
		
		//某个用户参与的卡片
		if (cardFrom.equals((short)2)) {
			list = cardsMapper.selectAttendByListPage(vo);
		}
		
		PageInfo result = new PageInfo(list);
		return result;
	}	

	@Override
	public int updateByPrimaryKey(Cards record) {
		return cardsMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Cards record) {
		return cardsMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<HashMap> totalByMonth(CardSearchVo vo) {
		return cardsMapper.totalByMonth(vo);
	}
	
	@Override
	public List<Cards> selectByReminds(CardSearchVo vo) {
		
		List<Cards> result = new ArrayList<Cards>();
		result = cardsMapper.selectByReminds(vo);
		return result;
	}
	
	/**
	 * 查找服务时间超过当前时间的卡片,并更新状态 = 3
	 * @return
	 */
	@Override
	public boolean updateFinishByOvertime() {
		cardsMapper.updateFinishByOvertime();
		return true;
	}
	
	
	@Override
	public boolean cardNotification(Cards card) {
		
		//1找出所有需要通知的用户集合 users
		Long cardId = card.getCardId();
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		
		if (attends.isEmpty()) return true;
		
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
		if (card.getSetNowSend().equals((short)0)) return true;
		
		
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
		
		return true;
	}
	
	private boolean pushToApp(Cards card, List<UserPushBind> userPushBinds) {
		
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		HashMap<String, String> tranParams = new HashMap<String, String>();

		Short cardType = card.getCardType();
		Long serviceTime = card.getServiceTime();
		String cardTypeName = CardUtil.getCardTypeName(cardType);
		String pushContent = CardUtil.getRemindContent(cardType, serviceTime);
		
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
					PushUtil.IOSPushToSingle(params);
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
			serviceContent = StringUtil.subStringByByte(serviceContent, 20) + "...";
		}
		
		String fromCityName = "";
		String toCityName = "";
		
		if (card.getCardType().equals((short)5)) {
			fromCityName = cityService.selectByCityId(card.getTicketFromCityId()).getName();
			toCityName = cityService.selectByCityId(card.getTicketToCityId()).getName();
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
//				statusName = "秘书叫早";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44668", content);				
				break;
			case 3:
//				statusName = "事务提醒";
				content = new String[] { createUserName, createUserMobile,  serviceTimeStr, serviceContent};
				sendSmsResult = SmsUtil.SendSms(mobile, "44666", content);						
				break;
			case 4:
//				statusName = "邀约通知";
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

	@Override
	public List<Cards> selectListByAddtimeTwo() {
		
		return cardsMapper.selectListByAddtimeTwo();
	}

	@Override
	public List<Cards> selectListByAddtimeThirty() {
		
		return cardsMapper.selectListByAddtimeThirty();
	}


}