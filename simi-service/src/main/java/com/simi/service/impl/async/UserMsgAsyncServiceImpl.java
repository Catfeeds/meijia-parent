package com.simi.service.impl.async;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.model.op.AppTools;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.async.UserMsgAsyncService;
import com.simi.service.card.CardAttendService;
import com.simi.service.feed.FeedService;
import com.simi.service.op.AppToolsService;
import com.simi.service.order.OrdersService;
import com.simi.service.partners.PartnerServiceTypeService;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;
import com.simi.service.xcloud.XcompanyCheckinService;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.user.UserMsgSearchVo;

@Service
public class UserMsgAsyncServiceImpl implements UserMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;

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
	
	@Autowired
	private AppToolsService appToolsService;
	
	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;

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
		record.setTitle(defaultUser.getName());
		record.setSummary("欢迎来到云行政，新手必读，立刻体验");
		record.setIconUrl(defaultUser.getHeadImg());
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
		record.setIconUrl("http://app.bolohr.com/images/icon/icon-kaoqin.png");
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
		noticeAppAsyncService.pushMsgToDevice(toUserId, "好友申请", fromUser.getName() + "请求加你为好友", "app", "friend_req", "", "");
		
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
		noticeAppAsyncService.pushMsgToDevice(fromUserId, "好友申请", summary, "app", "friend_req", "", "");
				
		
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
	public Future<Boolean> newActionAppMsg(Long userId, Long id, String extType, String title, String summary, String iconUrl) {

		// 根据服务大类ID 获得图标

		
		if (StringUtil.isEmpty(iconUrl)) {
			iconUrl = "http://img.bolohr.com/437396cc0b49b04dc89a0552f7e90cae?p=0";
			AppTools appTools = null;
			ApptoolsSearchVo searchVo = new ApptoolsSearchVo();
			searchVo.setAction(extType);
			
			List<AppTools> rs = appToolsService.selectBySearchVo(searchVo);
			if (!rs.isEmpty()) appTools = rs.get(0);
			if (appTools != null) {
				iconUrl = appTools.getLogo();
			}
		}

		UserMsgSearchVo searchVo = new UserMsgSearchVo();
		searchVo.setUserId(userId);
		searchVo.setCategory("app");
		searchVo.setAction(extType);
		searchVo.setParams(id.toString());
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
		record.setAction(extType);
		record.setParams(id.toString());
		record.setGotoUrl("");
		record.setTitle(title);
		record.setSummary(summary);
		record.setIconUrl(iconUrl);
		if (record.getMsgId() > 0L) {
			record.setUpdateTime(TimeStampUtil.getNowSecond());
			userMsgService.updateByPrimaryKey(record);
		} else {
			userMsgService.insert(record);
		}
		
		return new AsyncResult<Boolean>(true);
	}	


}
