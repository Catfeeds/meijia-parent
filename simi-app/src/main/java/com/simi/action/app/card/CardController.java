package com.simi.action.app.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meijia.utils.JsonUtil;
import com.meijia.utils.RegexUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardZan;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;
import com.simi.vo.card.LinkManVo;

@Controller
@RequestMapping(value = "/app/card")
public class CardController extends BaseController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardAttendService cardAttendService;	
	
	@Autowired
	private CardZanService cardZanService;	
	
	@Autowired
	private CardCommentService cardCommentService;	
	
	@Autowired
	private UserFriendService userFriendService;	

	// 卡片提交接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param card_type			卡片类型 0 = 通用(保留) 1 = 会议安排 2 = 秘书叫早 3 = 事务提醒 4 = 邀约通知 5 = 行程规划
	 *	@param create_user_id		创建卡片的用户ID
	 *  @param user_id  			用户ID
	 *	@param attends				参与人员，格式为json [{mobile:xxxx, name:张三}, {mobile:xxxx, name:李四}]
	 *	@param service_time			卡片发生时间， 时间戳格式，精确到秒
	 *	@param service_addr			卡片涉及的地址
	 *  @param service_content  	会议/提醒内容
	 *	@param set_remind			提醒设置 
									0 = 不提醒 
									1 = 按时提醒 
									2 = 5分钟 
									3 = 15分钟 
									4 = 提前30分钟
									5 = 提前一个小时
									6 = 提前2小时
									7 = 提前6小时
									8 = 提前一天
									9 = 提前两天
	 *	@param set_now_send			立即给相关人员消息 0 = 不发送 1 = 发送
	 *	@param set_sec_do			秘书处理 0 = 否 1 = 是
	 *	@param set_sec_remarks		给秘书捎句话
	 *  @param ticket_type			票务类型 0 = 无 1 = 飞机票 2 = 火车票（保留）
	 *  @param ticket_from_city_id 	去哪城市ID
	 *  @param ticket_to_city_id	到哪城市ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_card", method = RequestMethod.POST)
	public AppResultData<Object> postCard (
			@RequestParam(value = "card_id", required = false, defaultValue = "0") Long cardId,
			@RequestParam("card_type") Short cardType,
			@RequestParam("create_user_id") Long createUserId,
			@RequestParam("user_id") Long userId,
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
			@RequestParam(value = "status", required = false, defaultValue = "1") Short status
			) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

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
		record.setStatus(status);
		
		if (cardId > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.updateByPrimaryKeySelective(record);

		} else {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			cardService.insert(record);
			cardId = record.getCardId();			
		}
		
		//处理attends 转换为List<LinkManVo>的概念.
		if (!StringUtil.isEmpty(attends)) {
			Gson gson = new Gson();
			List<LinkManVo> linkManList = gson.fromJson(attends, new TypeToken<List<LinkManVo>>(){}.getType() ); 
			
			if (linkManList != null) {
				
				//先删除掉后再新增
				cardAttendService.deleteByCardId(cardId);
				LinkManVo item = null;
				for (int i = 0; i < linkManList.size(); i++) {
					item = (LinkManVo)linkManList.get(i);
					String mobile = item.getMobiel();
					if (StringUtil.isEmpty(mobile)) continue;
					if (!RegexUtil.isMobile(mobile)) continue;
					//根据手机号找出对应的userID, 如果没有则直接新增用户.
					Long newUserId = 0L;
					Users newUser = userService.getUserByMobile(mobile);
					if (newUser == null) {
						newUser = userService.genUser(mobile, item.getName(), (short) 2);
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
		
		//todo 1. 如果是立即给相关人员发送消息，则需要短信模板的通知.
		
		//todo 2. 如果是秘书处理，则需要给相应的秘书发送消息.
		
		result.setData(vo);
		return result;
	}		
	
	
	// 卡片详情接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "get_detail", method = RequestMethod.GET)
	public AppResultData<Object> getDetail(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		Cards record = cardService.selectByPrimaryKey(cardId);
		if (record == null) return result;
		CardViewVo vo = cardService.changeToCardViewVo(record);
		result.setData(vo);		
		return result;
	}
	
	// 卡片列表接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> getCardList(
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "service_date", required = false, defaultValue = "") String serviceDate,
			@RequestParam(value = "card_from", required = false, defaultValue = "0") Short cardFrom,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		//处理时间的问题
		Long startTime = 0L;
		Long endTime = 0L;
		
		if (StringUtil.isEmpty(serviceDate)) {
			String startTimeStr = serviceDate.substring(0, 10) + " 00:00:00";
			String endTimeStr = serviceDate.substring(0, 10) + " 23:59:59";
			startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
			endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardFrom(cardFrom);
		searchVo.setUserId(userId);
		
		if (startTime > 0L && endTime > 0L) {
			searchVo.setStartTime(startTime);
			searchVo.setEndTime(endTime);
		}
		
		List<Cards> cards = cardService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		
		if (!cards.isEmpty()) {
			List<CardViewVo> cardViewList = cardService.changeToCardViewVoBat(cards);
			result.setData(cardViewList);
		}
		
		return result;
	}	
	
	
	// 卡片点赞接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_zan", method = RequestMethod.POST)
	public AppResultData<Object> postZan(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardId(cardId);
		searchVo.setUserId(userId);
		CardZan cardZan = cardZanService.selectBySearchCardVo(searchVo);
		
		if (cardZan == null) {
			cardZan = cardZanService.initCardZan();
			cardZan.setCardId(cardId);
			cardZan.setUserId(userId);
			cardZanService.insert(cardZan);
		}
		return result;
	}
	
	// 卡片评论接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "post_comment", method = RequestMethod.POST)
	public AppResultData<Object> postComment(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId,
			@RequestParam("comment") String comment
			) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardId(cardId);
		searchVo.setUserId(userId);
		CardComment cardComment = cardCommentService.initCardComment();
		cardComment.setCardId(cardId);
		cardComment.setUserId(userId);
		cardComment.setComment(comment);
		cardCommentService.insert(cardComment);
		return result;
	}	
	
	// 卡片列表接口
	/**
	 *  @param card_id				卡片ID,  0 表示新增
	 *  @param user_id  			用户ID
	 *
	 *  @return  CardViewVo
	 */
	@RequestMapping(value = "get_comment_list", method = RequestMethod.GET)
	public AppResultData<Object> getCommentList(
			@RequestParam("card_id") Long cardId,
			@RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page
			) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		Users u = userService.getUserById(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}
		
		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardId(cardId);
		
		List<CardComment> cardComments = cardCommentService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		if (!cardComments.isEmpty()) {
			List<CardCommentViewVo> list = cardCommentService.changeToCardComments(cardComments);
			result.setData(list);
		}
		
		return result;
	}		
	
	
}
