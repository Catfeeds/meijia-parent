package com.simi.action.app.card;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardGrneralViewVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.LinkManVo;

@Controller
@RequestMapping(value = "/app/card")
public class CardGeneralController extends BaseController{
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private CardLogService cardLogService;
	
	@Autowired
	private CardAttendService cardAttendService;
	
	@Autowired
	private UserRef3rdService userRef3rdService;
	
	@Autowired
	private UserFriendService userFriendService;
	@RequestMapping(value = "post_card_default", method = RequestMethod.POST)
	public AppResultData<Object> postCard (
			@RequestParam(value = "card_id", required = false, defaultValue = "0") Long cardId,
			@RequestParam("card_type") Short cardType,
			@RequestParam("create_user_id") Long createUserId,
			@RequestParam("user_id") Long userId,
			@RequestParam("title") String title,
			@RequestParam("set_friend_view") Short setFriendView,
			@RequestParam(value = "poi_lng" , required = false, defaultValue = "") String poiLng,
			@RequestParam(value = "poi_lat", required = false, defaultValue = "") String poiLat,
			@RequestParam(value = "poi_name", required = false, defaultValue = "") String poiName,
			@RequestParam(value = "attends", required = false, defaultValue = "") String attends,
			@RequestParam("service_time") Long serviceTime,
			@RequestParam(value = "service_addr", required = false, defaultValue = "") String serviceAddr,
			@RequestParam("service_content") String serviceContent,
			@RequestParam("set_remind") Short setRemind,
			@RequestParam("set_now_send") Short setNowSend,
			@RequestParam("set_sec_do") Short setSecDo,
			@RequestParam(value = "set_sec_remarks", required = false, defaultValue = "") String setSecRemarks,
			@RequestParam(value = "ticket_type", required = false, defaultValue = "0") Short ticketType,
			@RequestParam(value = "ticket_from_city_id", required = false, defaultValue = "0") Long ticketFromCityId,
			@RequestParam(value = "ticket_to_city_id", required = false, defaultValue = "0") Long ticketToCityId,
			@RequestParam(value = "status", required = false, defaultValue = "1") Short status){
AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Cards record = cardService.initCards();
		if (cardId > 0L) {
			record = cardService.selectByPrimaryKey(cardId);
		}
		
		record.setCardId(cardId);
		record.setCreateUserId(createUserId);
		record.setUserId(userId);
		record.setCardType(cardType);
		record.setServiceTime(serviceTime);
		record.setServiceAddr(serviceAddr);
		record.setServiceContent(serviceContent);
		record.setSetRemind(setRemind);
		record.setSetNowSend(setNowSend);
		record.setSetSecDo(setSecDo);
		record.setSetSecRemarks(setSecRemarks);
		record.setTicketType(ticketType);
		record.setTicketFromCityId(ticketFromCityId);
		record.setTicketToCityId(ticketToCityId);
		
		if (!createUserId.equals(userId)) {
			Users createUser = userService.selectByPrimaryKey(createUserId);
			if (createUser.getUserType().equals((short)1)) {
				if (status.equals(1)) status = 2;
			}
		}
		
		record.setStatus(status);
		
		//添加卡片日志
		Users createUser = userService.selectByPrimaryKey(createUserId);
		
		CardLog cardLog = cardLogService.initCardLog();
		cardLog.setCardId(cardId);
		cardLog.setUserId(createUserId);
		
		String userName = createUser.getName().equals("") ? createUser.getMobile() : createUser.getName();
		cardLog.setUserName(userName);
		cardLog.setStatus(status);		
		
		if (cardId > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.updateByPrimaryKeySelective(record);
			
			cardLog.setRemarks(userName + "修改了卡片信息.");
		} else {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.insert(record);
			cardId = record.getCardId();		
			
			cardLog.setRemarks(userName + "创建了卡片信息.");
		}
		
		cardLogService.insert(cardLog);
		
		
//		System.out.println(attends);
		//处理attends 转换为List<LinkManVo>的概念.
		if (!StringUtil.isEmpty(attends)) {
			Gson gson = new Gson();
			List<LinkManVo> linkManList = gson.fromJson(attends, new TypeToken<List<LinkManVo>>(){}.getType() ); 
			System.out.println(linkManList.toString());
			if (linkManList != null) {
				
				//先删除掉后再新增
				cardAttendService.deleteByCardId(cardId);
				LinkManVo item = null;
				for (int i = 0; i < linkManList.size(); i++) {
					System.out.println(linkManList.get(i).toString());
					item = linkManList.get(i);
					String mobile = item.getMobile();
					
					if (!RegexUtil.isMobile(mobile)) {
						continue;		
					}
					
					
					if (StringUtil.isEmpty(mobile)) continue;
					if (!RegexUtil.isMobile(mobile)) continue;
					//根据手机号找出对应的userID, 如果没有则直接新增用户.
					Long newUserId = 0L;
					Users newUser = userService.selectByMobile(mobile);
					if (newUser == null) {
						newUser = userService.genUser(mobile, item.getName(), (short) 2);
						
						UserRef3rd userRef3rd = userRef3rdService.selectByUserIdForIm(newUser.getId());
						if(userRef3rd == null){
							userService.genImUser(newUser);
						}						
					}
					newUserId = newUser.getId();
					
					newUserId = newUser.getId();
					CardAttend cardAttend = cardAttendService.initCardAttend();
					cardAttend.setCardId(cardId);
					cardAttend.setUserId(newUserId);
					cardAttend.setMobile(mobile);
					cardAttend.setName(item.getName());
					cardAttendService.insert(cardAttend);
					
					
					//相互加为好友.
					userFriendService.addFriends(u, newUser);
				}
			}
		}
		
		CardViewVo vo = cardService.changeToCardViewVo(record);
		
	//	CardGrneralViewVo vo = cardService.changeToCardGrneralViewVo(record);
		
		//todo 1. 如果是立即给相关人员发送消息，则需要短信模板的通知.
		if (cardId.equals(0L) && setNowSend.equals((short)1)) {
			cardService.cardNotification(record);
		}
		//todo 2. 如果是秘书处理，则需要给相应的秘书发送消息.
		
		result.setData(vo);
		return result;
		
	}
}
