package com.simi.service.impl.async;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.meijia.utils.MobileUtil;
import com.meijia.utils.StringUtil;
import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserLeavePass;
import com.simi.po.model.user.UserMsg;
import com.simi.po.model.user.Users;
import com.simi.service.async.LeaveMsgAsyncService;
import com.simi.service.async.NoticeAppAsyncService;
import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UserLeaveService;
import com.simi.service.user.UserMsgService;
import com.simi.service.user.UsersService;

@Service
public class LeaveMsgAsyncServiceImpl implements LeaveMsgAsyncService {

	@Autowired
	public UsersService usersService;

	@Autowired
	private UserMsgService userMsgService;

	@Autowired
	private UserLeaveService userLeaveService;

	@Autowired
	private UserLeavePassService userLeavePassService;

	@Autowired
	private NoticeAppAsyncService noticeAppAsyncService;


	@Async
	@Override
	public Future<Boolean> newLeaveMsg(Long userId, Long leaveId) {

		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		if (userLeave == null)
			return new AsyncResult<Boolean>(true);

		Users u = usersService.selectByPrimaryKey(userId);
		
		String leveTypeName = userLeaveService.getLeaveTypeName(userLeave.getLeaveType());
		// 给自己产生消息
		UserMsg record = userMsgService.initUserMsg();
		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("leave_pass");
		record.setParams(leaveId.toString());
		record.setTitle("请假申请");
		record.setSummary("你申请了" + userLeave.getTotalDays() + leveTypeName + ".");
		record.setIconUrl("http://123.57.173.36/images/icon/icon-qingjia.png");
		userMsgService.insert(record);

		// 给审批人都发送消息.
		List<UserLeavePass> passUsers = userLeavePassService.selectByLeaveId(leaveId);

		for (UserLeavePass item : passUsers) {
			if (item.getPassUserId() == null || item.getPassUserId().equals(0L))
				continue;

			String msgContent = u.getName() + "申请" + userLeave.getTotalDays()  + leveTypeName + ",请查看.";

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
			noticeAppAsyncService.pushMsgToDevice(item.getPassUserId(), "请假审批", msgContent, "app", "leave_pass", leaveId.toString(), "");
		}

		return new AsyncResult<Boolean>(true);
	}
	
	@Async
	@Override
	public Future<Boolean> newLeavePassMsg(Long passUserId, Long leaveId) {

		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		if (userLeave == null)
			return new AsyncResult<Boolean>(true);
		
		Long userId = userLeave.getUserId();
		
		Users passUser = usersService.selectByPrimaryKey(passUserId);
		Users user = usersService.selectByPrimaryKey(userId);
		
		String passName = passUser.getName();
		if (StringUtil.isEmpty(passName)) passName = MobileUtil.getMobileStar(passUser.getMobile());
		
		String name = user.getName();
		if (StringUtil.isEmpty(name)) name = MobileUtil.getMobileStar(passUser.getMobile());
		
		Short status = userLeave.getStatus();
		String statusName = "同意";
		if (status.equals((short)2)) statusName = "拒绝";
		
		String leveTypeName = userLeaveService.getLeaveTypeName(userLeave.getLeaveType());
		
		String msgContent = "你" + statusName + "了" + name + leveTypeName +"申请（"+userLeave.getTotalDays()+"）";
		

		// 给审批人产生消息
		UserMsg record = userMsgService.initUserMsg();
		record.setUserId(userId);
		record.setFromUserId(userId);
		record.setToUserId(userId);
		record.setCategory("app");
		record.setAction("leave_pass");
		record.setParams(leaveId.toString());
		record.setTitle("请假申请");
		record.setSummary(msgContent);
		record.setIconUrl("http://123.57.173.36/images/icon/icon-qingjia.png");
		userMsgService.insert(record);

		msgContent = passName + statusName + "了你的"+leveTypeName+"申请（"+userLeave.getTotalDays()+"）";
		// 发送给申请人推送消息
		noticeAppAsyncService.pushMsgToDevice(userId, "请假审批", msgContent, "app", "leave_pass", leaveId.toString(), "");
		

		return new AsyncResult<Boolean>(true);
	}
	
	@Async
	@Override
	public Future<Boolean> newLeaveCancelMsg(Long leaveId) {

		UserLeave userLeave = userLeaveService.selectByPrimaryKey(leaveId);
		if (userLeave == null)
			return new AsyncResult<Boolean>(true);
		
		Long userId = userLeave.getUserId();
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		String name = u.getName();
		if (StringUtil.isEmpty(name)) name = MobileUtil.getMobileStar(u.getMobile());
		// 给审批人都发送消息.
		List<UserLeavePass> passUsers = userLeavePassService.selectByLeaveId(leaveId);
		
//		String leveTypeName = userLeaveService.getLeaveTypeName(userLeave.getLeaveType());
		
		for (UserLeavePass item : passUsers) {
			if (item.getPassUserId() == null || item.getPassUserId().equals(0L))
				continue;
		
			String msgContent = u.getName() + "撤消了请假申请，请知悉";

			// 发送推送消息
			noticeAppAsyncService.pushMsgToDevice(item.getPassUserId(), "请假审批", msgContent, "app", "leave_pass", leaveId.toString(), "");
		}

		return new AsyncResult<Boolean>(true);
	}	
}
