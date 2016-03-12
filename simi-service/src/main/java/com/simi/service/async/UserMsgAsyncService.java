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

	Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent);

	Future<Boolean> newActionAppMsg(Long userId, Long id, String extType, String title, String summary);


	
}