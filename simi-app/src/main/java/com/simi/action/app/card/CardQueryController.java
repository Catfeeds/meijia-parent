package com.simi.action.app.card;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.meijia.utils.DateUtil;
import com.meijia.utils.MathBigDeciamlUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.action.app.BaseController;
import com.simi.common.ConstantMsg;
import com.simi.common.Constants;
import com.simi.po.model.card.CardComment;
import com.simi.po.model.card.CardLog;
import com.simi.po.model.card.Cards;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardCommentService;
import com.simi.service.card.CardLogService;
import com.simi.service.card.CardService;
import com.simi.service.card.CardZanService;
import com.simi.service.user.UserFriendService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UsersService;
import com.simi.utils.CardUtil;
import com.simi.vo.AppResultData;
import com.simi.vo.card.CardCommentViewVo;
import com.simi.vo.card.CardListVo;
import com.simi.vo.card.CardRemindVo;
import com.simi.vo.card.CardSearchVo;
import com.simi.vo.card.CardViewVo;

@Controller
@RequestMapping(value = "/app/card")
public class CardQueryController extends BaseController {
	@Autowired
	private UsersService userService;

	@Autowired
	private CardService cardService;

	@Autowired
	private CardAttendService cardAttendService;

	@Autowired
	private CardZanService cardZanService;

	@Autowired
	private CardLogService cardLogService;

	@Autowired
	private CardCommentService cardCommentService;

	@Autowired
	private UserFriendService userFriendService;

	@Autowired
	private UserRef3rdService userRef3rdService;
	
	@Autowired
	private UserPushBindService userPushBindService;

	// 卡片详情接口
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "get_detail", method = RequestMethod.GET)
	public AppResultData<Object> getDetail(@RequestParam("card_id") Long cardId,
			@RequestParam(value = "user_id", required = false, defaultValue = "") Long userId) throws JsonParseException, JsonMappingException, IOException {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		if (userId != null) {
			Users u = userService.selectByPrimaryKey(userId);

			// 判断是否为注册用户，非注册用户返回 999
			if (u == null) {
				result.setStatus(Constants.ERROR_999);
				result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
				return result;
			}
		}
		Cards record = cardService.selectByPrimaryKey(cardId);
		if (record == null)
			return result;
		CardViewVo vo = cardService.changeToCardViewVo(record);
		result.setData(vo);
		return result;
	}

	// 卡片列表接口
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "get_list", method = RequestMethod.GET)
	public AppResultData<Object> getCardList(@RequestParam("user_id") Long userId,
			@RequestParam(value = "service_date", required = false, defaultValue = "") String serviceDate,
			@RequestParam(value = "card_from", required = false, defaultValue = "0") Short cardFrom,
			@RequestParam(value = "card_type", required = false, defaultValue = "0") Short cardType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "lat", required = false, defaultValue = "") String lat,
			@RequestParam(value = "lng", required = false, defaultValue = "") String lng) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		// 处理时间的问题
		Long startTime = 0L;
		Long endTime = 0L;

		if (!StringUtil.isEmpty(serviceDate)) {
			Date serviceDateObj = DateUtil.parse(serviceDate);

			String startTimeStr = DateUtil.format(serviceDateObj, "yyyy-MM-dd 00:00:00");
			String endTimeStr = DateUtil.format(serviceDateObj, "yyyy-MM-dd 23:59:59");
			startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
			endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;
		}

		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardFrom(cardFrom);
		searchVo.setUserId(userId);
		searchVo.setUserType(u.getUserType());

		if (!cardType.equals((short) 0)) {
			searchVo.setCardType(cardType);
		}

		if (startTime > 0L && endTime > 0L) {
			searchVo.setStartTime(startTime);
			searchVo.setEndTime(endTime);
		}

		PageInfo pageInfo = cardService.selectByListPage(searchVo, page, Constants.PAGE_MAX_NUMBER);
		List<Cards> cards = pageInfo.getList();
		List<CardListVo> cardList = new ArrayList<CardListVo>();
		if (!cards.isEmpty()) {
			cardList = cardService.changeToCardListVo(cards);
			result.setData(cardList);
		}

		return result;
	}

	// 卡片评论列表接口
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "get_comment_list", method = RequestMethod.GET)
	public AppResultData<Object> getCommentList(@RequestParam("card_id") Long cardId, @RequestParam("user_id") Long userId,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page) throws JsonParseException, JsonMappingException, IOException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

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

	// 卡片日志列表
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 */
	@RequestMapping(value = "get_logs", method = RequestMethod.GET)
	public AppResultData<Object> getLogs(@RequestParam("user_id") Long userId, @RequestParam("card_id") Long cardId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		List<CardLog> list = cardLogService.selectByCardId(cardId);
		result.setData(list);
		return result;
	}

	// 按照年月查看卡片个数
	/**
	 * @param card_id
	 *            卡片ID, 0 表示新增
	 * @param user_id
	 *            用户ID
	 *
	 * @return CardViewVo
	 */
	@RequestMapping(value = "total_by_month", method = RequestMethod.GET)
	public AppResultData<Object> getLogs(@RequestParam("user_id") Long userId, @RequestParam("year") int year, @RequestParam("month") int month) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		String startTimeStr = DateUtil.getFirstDayOfMonth(year, month) + " 00:00:00";
		String endTimeStr = DateUtil.getLastDayOfMonth(year, month) + " 23:59:59";

		Long startTime = TimeStampUtil.getMillisOfDayFull(startTimeStr) / 1000;
		Long endTime = TimeStampUtil.getMillisOfDayFull(endTimeStr) / 1000;

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setStartTime(startTime);
		searchVo.setEndTime(endTime);
		searchVo.setUserId(userId);
		searchVo.setUserType(u.getUserType());

		List<HashMap> monthDatas = cardService.totalByMonth(searchVo);

		result.setData(monthDatas);
		return result;
	}

	// 获取该用户所有需要提醒的卡片列表详情.考虑到性能，只传递最基础的信息，并不包括全部的卡片详情.
	@RequestMapping(value = "get_reminds", method = RequestMethod.GET)
	public AppResultData<Object> getReminds(@RequestParam("user_id") Long userId) {
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		Users u = userService.selectByPrimaryKey(userId);

		// 判断是否为注册用户，非注册用户返回 999
		if (u == null) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg(ConstantMsg.USER_NOT_EXIST_MG);
			return result;
		}

		CardSearchVo searchVo = new CardSearchVo();
		searchVo.setCardFrom((short) 0);
		searchVo.setUserId(userId);
		searchVo.setUserType(u.getUserType());

		List<Cards> cards = cardService.selectByReminds(searchVo);

		List<CardRemindVo> remindCards = new ArrayList<CardRemindVo>();
		Cards vo = null;

		Long nowSecond = TimeStampUtil.getNowSecond();
		for (int i = 0; i < cards.size(); i++) {
			vo = cards.get(i);
			Short setRemind = vo.getSetRemind();
			int remindMin = CardUtil.getRemindMin(setRemind);

			Long serviceTime = vo.getServiceTime();
			Short cardType = vo.getCardType();
			Long remindTime = serviceTime - remindMin * 60;

			if (remindTime > nowSecond) {
				CardRemindVo item = new CardRemindVo();
				item.setCardId(vo.getCardId());

				item.setCardType(cardType);
				item.setCardTypeName(CardUtil.getCardTypeName(cardType));
				item.setServiceTime(serviceTime * 1000);
				item.setRemindTime(remindTime * 1000);
				item.setRemindContent(CardUtil.getRemindContent(cardType, serviceTime));
				remindCards.add(item);
			}
		}

		result.setData(remindCards);

		return result;
	}

	@RequestMapping(value = "push-setclock", method = RequestMethod.GET)
	public AppResultData<Object> pushSetClock(@RequestParam("card_id") Long cardId,
			@RequestParam(value = "clinet_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,

			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow() + 120000;

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		String timeStr = time1.toString();
		tranParams.put("is_show", "true");
		tranParams.put("action", "setclock");
		tranParams.put("card_id", cardId.toString());
		tranParams.put("card_type", "1");
		tranParams.put("service_time", timeStr);
		tranParams.put("remind_time", timeStr);
		tranParams.put("remind_title", "会议安排");
		tranParams.put("remind_content", timeStr1 + "请参加华北区电话会议,请提前10分钟进入会议室B11");

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "push-alarm", method = RequestMethod.GET)
	public AppResultData<Object> pushAlarm(@RequestParam("card_id") Long cardId,
			@RequestParam(value = "clinet_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,

			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		String timeStr = time1.toString();
		tranParams.put("is_show", "true");
		tranParams.put("action", "alarm");
		tranParams.put("card_id", cardId.toString());
		tranParams.put("card_type", "1");
		tranParams.put("service_time", timeStr);
		tranParams.put("remind_time", timeStr);
		tranParams.put("remind_title", "会议安排");
		tranParams.put("remind_content", timeStr1 + "请参加华北区电话会议,请提前10分钟进入会议室B11");

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "push-msg", method = RequestMethod.GET)
	public AppResultData<Object> pushMsg(
			@RequestParam(value = "client_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,

			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "MM-dd HH:mm");

		tranParams.put("is_show", "true");
		tranParams.put("action", "msg");
		tranParams.put("card_id", "0");
		tranParams.put("card_type", "0");
		tranParams.put("service_time", "");
		tranParams.put("remind_time", "");
		tranParams.put("remind_title", "新消息");
		tranParams.put("remind_content", "你在" + timeStr1 + "有一条新的消息");

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}

	@RequestMapping(value = "car-msg", method = RequestMethod.GET)
	public AppResultData<Object> cardMsg(
			@RequestParam(value = "client_id", required = false, defaultValue = "b10510a6a8d000fb024af47271f8a49f") String clientId,
			@RequestParam(value = "device_type", required = false, defaultValue = "ios") String deviceType) throws Exception {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		List<UserPushBind> exists = userPushBindService.selectByClientId(clientId);
		if (!exists.isEmpty()) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("client_id 不存在");
			return result;
		}
		
		UserPushBind userBind = exists.get(0);
		if (!userBind.getDeviceType().equals(deviceType)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("设备类型不正确。");
			return result;
		}
		
		
		
		Long userId = userBind.getUserId();
		Users u = userService.selectByPrimaryKey(userId);
		String mobile = u.getMobile();
		BigDecimal b = u.getRestMoney();
		String restMoney = MathBigDeciamlUtil.round2(b);
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cid", clientId);

		HashMap<String, String> tranParams = new HashMap<String, String>();

		Long time1 = TimeStampUtil.getNow();

		String timeStr1 = TimeStampUtil.timeStampToDateStr(time1, "yyyy-MM-dd HH:mm:ss");
				
		tranParams.put("is_show", "false");
		tranParams.put("action", "car-msg");
		tranParams.put("car_no", "京EX9603");
		tranParams.put("car_color", "白色");
		tranParams.put("mobile", mobile);
		tranParams.put("ocx_time", timeStr1);
		tranParams.put("order_money", "10元");
		tranParams.put("rest_money", restMoney);
		tranParams.put("user_id", userId.toString());

		// JsonObject jsonParams = JsonUtil.mapTojson(tranParams);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonParams = objectMapper.writeValueAsString(tranParams);
		System.out.println(jsonParams);

		params.put("transmissionContent", jsonParams);

		if (deviceType.equals("ios")) {
			PushUtil.IOSPushToSingle(params, "notification");
		}

		if (deviceType.equals("android")) {
			PushUtil.AndroidPushToSingle(params);
		}

		return result;
	}
}
