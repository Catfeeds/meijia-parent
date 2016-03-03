package com.simi.service.impl.async;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijia.utils.MeijiaUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.push.PushUtil;
import com.simi.common.Constants;
import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;
import com.simi.po.model.feed.Feeds;
import com.simi.po.model.order.Orders;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserLeavePass;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.UserPushBind;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.card.CardService;
import com.simi.service.feed.FeedService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UserPushBindService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.utils.CardUtil;
import com.simi.vo.UserMsgSearchVo;

@Service
public class UserMsgAsyncServiceImpl implements UserMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserPushBindService userPushBindService;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private CardService cardService;

	@Autowired
	private CardAttendService cardAttendService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private XcompanyCheckinService xcompanyCheckinService;

	@Autowired
	private UserLeaveService userLeaveService;

	@Autowired
	private UserLeavePassService userLeavePassService;

	@Autowired
	private PartnerServiceTypeService partnerServiceTypeService;

	@Autowired
	private OrdersService ordersService;

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
		if (card == null)
			return new AsyncResult<Boolean>(true);

		Long createUserId = card.getCreateUserId();
		// 找出所有需要发送消息人员:
		// 获取到参会、参与人员列表
		List<CardAttend> attends = cardAttendService.selectByCardId(cardId);
		List<Long> userIds = new ArrayList<Long>();

		for (CardAttend item : attends) {
			if (!userIds.contains(item.getUserId())) {
				userIds.add(item.getUserId());
			}
		}

		if (!userIds.contains(createUserId))
			userIds.add(createUserId);

		String serviceContent = card.getServiceContent();
		if (serviceContent != null && serviceContent.length() > 20) {
			serviceContent = serviceContent.substring(0, 20);
		}

		if (serviceContent.length() == 0) {
			serviceContent = "你创建了新的日程安排";
		}

		for (Long uid : userIds) {
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
		if (feed == null)
			return new AsyncResult<Boolean>(true);

		Long userId = feed.getUserId();
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

		if (title != null && title.length() > 20) {
			title = title.substring(0, 20);
		}

		if (title.length() == 0) {
			title = "你发布了新的动态";
		}

		record.setSummary(title);
		record.setIconUrl("http://123.57.173.36/images/icon/iconfont-dongtai.png");
		userMsgService.insert(record);

		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Override
	public Future<Boolean> newImMsg(Long fromUserId, String fromUserName, Long toUserId, String toUserName, String imgContent) {

		// 需要分别往发送者和接收者都存储。
		Users fromUser = usersService.selectByPrimaryKey(fromUserId);
		Users toUser = usersService.selectByPrimaryKey(toUserId);

		// 1. 往发送者存储消息
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

		// 2. 往接收者存储消息
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
		toMsg.setFromUserId(toUserId);
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

	@Async
	@Override
	public Future<Boolean> newCheckinMsg(Long userId, Long checkId) {

		// XcompanyCheckin checkin =
		// xcompanyCheckinService.selectByPrimarykey(checkId);

		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCategory("app");
		searchVo.setAction("checkin");
		searchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		searchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<UserMsg> rsList = userMsgService.selectBySearchVo(searchVo);

		UserMsg record = userMsgService.initUserMsg();
		if (!rsList.isEmpty()) {
			record = rsList.get(0);
		}

		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("checkin");
		record.setParams("");
		record.setGotoUrl("");
		record.setTitle("考勤");
		record.setSummary("完成一次考勤记录");
		record.setIconUrl("http://123.57.173.36/images/icon/icon-kaoqin.png");
		if (record.getMsgId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(record);
		} else {
			userMsgService.insert(record);
		}

		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Override
	public Future<Boolean> newLeaveMsg(Long userId, Long leaveId) {

		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		if (userLeave == null)
			return new AsyncResult<Boolean>(true);

		Users u = usersService.selectByPrimaryKey(userId);

		// 给自己发送消息
		UserMsg record = userMsgService.initUserMsg();
		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("leave");
		record.setParams(leaveId.toString());
		record.setTitle("请假申请");
		record.setSummary("你申请了" + userLeave.getTotalDays() + "天请假.");
		record.setIconUrl("http://123.57.173.36/images/icon/icon-qingjia.png");
		userMsgService.insert(record);

		// 给审批人都发送消息.
		List<UserLeavePass> passUsers = userLeavePassService.selectByLeaveId(leaveId);

		for (UserLeavePass item : passUsers) {
			if (item.getPassUserId() == null || item.getPassUserId().equals(0L))
				continue;

			String msgContent = u.getName() + "申请" + userLeave.getTotalDays() + "天请假,请查看.";

			UserMsg passRecord = userMsgService.initUserMsg();
			passRecord.setUserId(item.getPassUserId());
			passRecord.setFromUserId(userId);
			passRecord.setToUserId(item.getPassUserId());
			passRecord.setCategory("app");
			passRecord.setAction("leave_pass");
			passRecord.setParams(leaveId.toString());
			passRecord.setTitle("请假审批");
			passRecord.setSummary(msgContent);
			passRecord.setIconUrl("http://123.57.173.36/images/icon/icon-qingjia.png");
			userMsgService.insert(passRecord);

			// 发送推送消息
			pushMsgToDevice(item.getPassUserId(), "请假审批", msgContent);
		}

		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Override
	public Future<Boolean> newFriendMsg(Long fromUserId, Long toUserId) {
		// 需要分别往发送者和接收者都存储。
		Users fromUser = usersService.selectByPrimaryKey(fromUserId);
		Users toUser = usersService.selectByPrimaryKey(toUserId);

		// 1. 往发送者存储消息
		UserMsgSearchVo fromSearchVo = new UserMsgSearchVo();
		fromSearchVo.setFromUserId(fromUserId);
		fromSearchVo.setToUserId(toUserId);
		fromSearchVo.setCategory("app");
		fromSearchVo.setAction("friends");
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
		fromMsg.setAction("friends");
		fromMsg.setParams(toUserId.toString());
		fromMsg.setGotoUrl("");
		fromMsg.setTitle("好友添加提醒");
		fromMsg.setSummary("你添加了" + toUser.getName() + "为好友");
		fromMsg.setIconUrl(toUser.getHeadImg());
		if (fromMsg.getMsgId() > 0L) {
			fromMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(fromMsg);
		} else {
			userMsgService.insert(fromMsg);
		}

		// 2. 往接收者存储消息
		UserMsgSearchVo toSearchVo = new UserMsgSearchVo();
		toSearchVo.setFromUserId(toUserId);
		toSearchVo.setToUserId(fromUserId);
		toSearchVo.setCategory("app");
		toSearchVo.setAction("friends");
		toSearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		toSearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<UserMsg> toList = userMsgService.selectBySearchVo(toSearchVo);

		UserMsg toMsg = userMsgService.initUserMsg();
		if (!toList.isEmpty()) {
			toMsg = toList.get(0);
		}

		toMsg.setUserId(toUserId);
		toMsg.setFromUserId(toUserId);
		toMsg.setToUserId(fromUserId);
		toMsg.setCategory("app");
		toMsg.setAction("friends");
		toMsg.setParams(fromUserId.toString());
		toMsg.setGotoUrl("");
		toMsg.setTitle("好友添加申请");
		toMsg.setSummary(fromUser.getName() + "请求加你为好友");
		toMsg.setIconUrl(fromUser.getHeadImg());
		if (toMsg.getMsgId() > 0L) {
			toMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(toMsg);
		} else {
			userMsgService.insert(toMsg);
		}

		// 发送推送消息（接受者）
		pushMsgToDevice(toUserId, "好友申请", fromUser.getName() + "请求加你为好友");
		
		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Override
	public Future<Boolean> newFriendReqMsg(Long fromUserId, Long toUserId, Short status) {
		// 需要分别往发送者和接收者都存储。
		Users fromUser = usersService.selectByPrimaryKey(fromUserId);
		Users toUser = usersService.selectByPrimaryKey(toUserId);

		// 1. 往发送者存储消息
		UserMsgSearchVo fromSearchVo = new UserMsgSearchVo();
		fromSearchVo.setFromUserId(fromUserId);
		fromSearchVo.setToUserId(toUserId);
		fromSearchVo.setCategory("app");
		fromSearchVo.setAction("friends");
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
		fromMsg.setAction("friends");
		fromMsg.setParams(toUserId.toString());
		fromMsg.setGotoUrl("");
		fromMsg.setTitle("好友申请");
		String summary = "";
		if (status.equals((short) 1)) {
			summary = toUser.getName() + "已经同意并添加了你为好友";
		}
		if (status.equals((short) 2)) {
			summary = toUser.getName() + "拒绝了添加你为好友";
		}
		fromMsg.setSummary(summary);

		fromMsg.setIconUrl(toUser.getHeadImg());
		if (fromMsg.getMsgId() > 0L) {
			fromMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(fromMsg);
		} else {
			userMsgService.insert(fromMsg);
		}
		
		// 发送推送消息（发送者）
		pushMsgToDevice(toUserId, "好友申请", summary);
				
		
		// 2. 往接收者存储消息
		UserMsgSearchVo toSearchVo = new UserMsgSearchVo();
		toSearchVo.setFromUserId(toUserId);
		toSearchVo.setToUserId(fromUserId);
		toSearchVo.setCategory("app");
		toSearchVo.setAction("friends");
		toSearchVo.setStartTime(TimeStampUtil.getBeginOfToday());
		toSearchVo.setEndTime(TimeStampUtil.getEndOfToday());
		List<UserMsg> toList = userMsgService.selectBySearchVo(toSearchVo);

		UserMsg toMsg = userMsgService.initUserMsg();
		if (!toList.isEmpty()) {
			toMsg = toList.get(0);
		}
		toMsg.setUserId(toUserId);
		toMsg.setFromUserId(toUserId);
		toMsg.setToUserId(fromUserId);
		toMsg.setCategory("app");
		toMsg.setAction("friends");
		toMsg.setParams(fromUserId.toString());
		toMsg.setGotoUrl("");
		toMsg.setTitle("好友申请");
		if (status.equals((short) 1)) {
			toMsg.setSummary("你已经同意并添加" + fromUser.getName() + "为好友");
		}
		if (status.equals((short) 2)) {
			toMsg.setSummary("你拒绝了添加" + fromUser.getName() + "为好友");
		}
		toMsg.setIconUrl(fromUser.getHeadImg());
		if (toMsg.getMsgId() > 0L) {
			toMsg.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(toMsg);
		} else {
			userMsgService.insert(toMsg);
		}
		return new AsyncResult<Boolean>(true);
	}

	@Async
	@Override
	public Future<Boolean> newOrderMsg(Long userId, Long orderId, String orderExtType, String summary) {

		Orders order = ordersService.selectByPrimaryKey(orderId);
		if (order == null)
			return new AsyncResult<Boolean>(true);

		// 得到服务大类名称
		Long serviceTypeId = order.getServiceTypeId();
		PartnerServiceType serviceType = partnerServiceTypeService.selectByPrimaryKey(serviceTypeId);

		// 根据订单状态获得描述信息.
		String orderSummary = MeijiaUtil.getOrderStausMsg(order.getOrderStatus());
		if (!StringUtil.isEmpty(summary)) {
			orderSummary = summary;
		}

		// 根据服务大类ID 获得图标
		String iconUrl = getIconByServiceTypeId(serviceTypeId);

		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCategory("app");
		searchVo.setAction(orderExtType);
		searchVo.setParams(orderId.toString());
		List<UserMsg> rsList = userMsgService.selectBySearchVo(searchVo);

		UserMsg record = userMsgService.initUserMsg();
		if (!rsList.isEmpty()) {
			record = rsList.get(0);
		}

		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction(orderExtType);
		record.setParams(orderId.toString());
		record.setGotoUrl("");
		record.setTitle(serviceType.getName());
		record.setSummary(orderSummary);
		record.setIconUrl(iconUrl);
		if (record.getMsgId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(record);
		} else {
			userMsgService.insert(record);
		}
		
		//发送推送消息
		if (order.getOrderStatus().equals(Constants.ORDER_STATUS_2_PAY_DONE) ||
			order.getOrderStatus().equals(Constants.ORDER_STATUS_3_PROCESSING)) {
			pushMsgToDevice(userId, serviceType.getName(), orderSummary);
		}
		
		return new AsyncResult<Boolean>(true);
	}

	// 发送推送消息
	@Async
	@Override
	public Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent) {

		// 发送推送消息（接受者）
		UserPushBind userPushBind = userPushBindService.selectByUserId(userId);

		if (userPushBind == null)
			return new AsyncResult<Boolean>(true);
		if (StringUtil.isEmpty(userPushBind.getClientId()))
			return new AsyncResult<Boolean>(true);

		HashMap<String, String> params = new HashMap<String, String>();

		HashMap<String, String> tranParams = new HashMap<String, String>();

		tranParams.put("is_show", "true");
		tranParams.put("action", "msg");
		tranParams.put("card_id", "0");
		tranParams.put("card_type", "0");
		tranParams.put("service_time", "");
		tranParams.put("remind_time", "");
		tranParams.put("remind_title", msgTitle);
		tranParams.put("remind_content", msgContent);

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonParams = "";
		try {
			jsonParams = objectMapper.writeValueAsString(tranParams);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		params.put("transmissionContent", jsonParams);
		params.put("cid", userPushBind.getClientId());

		if (userPushBind.getDeviceType().equals("ios")) {
			try {
				PushUtil.IOSPushToSingle(params, "notification");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (userPushBind.getDeviceType().equals("android")) {
			try {
				PushUtil.AndroidPushToSingle(params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new AsyncResult<Boolean>(true);
	}

	// 根据服务大类获得对应的图表
	private String getIconByServiceTypeId(Long serviceTypeId) {
		String iconUrl = "";

		switch (serviceTypeId.toString()) {
		// 团建
		case "79":
			iconUrl = "http://img.51xingzheng.cn/c14e06eb395592ae3dbefda371e7a410?p=0";
			break;
		// 保洁
		case "204":
			iconUrl = "http://img.51xingzheng.cn/879905845779a81df1e0a670411dc22f?p=0";
			break;
		// 绿植
		case "238":
			iconUrl = "http://img.51xingzheng.cn/e471c96a527a807f1b3e862c45c753f4?p=0";
			break;
		// 送水
		case "239":
			iconUrl = "http://img.51xingzheng.cn/e5e4ba5855916bcdc516056e0176cc93?p=0";
			break;
		}

		return iconUrl;
	}

}
