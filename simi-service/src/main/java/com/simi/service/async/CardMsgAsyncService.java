package com.simi.service.async;

import java.util.concurrent.Future;

public interface CardMsgAsyncService {

	Future<Boolean> newCardMsg(Long cardId);
	
}