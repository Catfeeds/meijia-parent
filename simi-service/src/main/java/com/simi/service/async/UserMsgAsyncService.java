package com.simi.service.async;

import java.util.concurrent.Future;

public interface UserMsgAsyncService {

	Future<Boolean> newUserMsg(Long userId);

	Future<Boolean> newCardMsg(Long cardId);

	Future<Boolean> newFeedMsg(Long fid);


	
}