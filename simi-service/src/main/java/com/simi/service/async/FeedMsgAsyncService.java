package com.simi.service.async;

import java.util.concurrent.Future;

public interface FeedMsgAsyncService {

	Future<Boolean> newFeedMsg(Long fid);

	Future<Boolean> newFeedZanMsg(Long userId, Long fid, Short feedType, Long commentId);

	Future<Boolean> newFeedCommentMsg(Long commentUserId, Long fid, Short feedType, Long commentId);

	Future<Boolean> newFeedDoneMsg(Long userId, Long fid, Short feedType, Long commentId);

	Future<Boolean> newFeedCloseMsg(Long userId, Long fid, Short feedType);
	
}