package com.simi.service.async;

import java.util.concurrent.Future;

public interface UserMsgAsyncService {

	Future<Boolean> newUserMsg(Long userId);

	Future<Boolean> newCardMsg(Long cardId);

	Future<Boolean> newFeedMsg(Long fid);

	Future<Boolean> newImMsg(Long fromUserId, String fromUserName, Long toUserId, String toUserName, String imgContent);

	Future<Boolean> newCheckinMsg(Long userId, Long checkId);

	Future<Boolean> newLeaveMsg(Long userId, Long leaveId);

	Future<Boolean> newFriendMsg(Long fromUserId,Long toUserId);
	
	Future<Boolean> newFriendReqMsg(Long fromUserId, Long toUserId, Short status);

	Future<Boolean> newOrderMsg(Long userId, Long orderId, String orderExtType, String orderExtTitle);

	Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent);


	
}