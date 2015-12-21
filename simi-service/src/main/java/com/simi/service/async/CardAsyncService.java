package com.simi.service.async;

import java.util.concurrent.Future;

import com.simi.po.model.card.Cards;

public interface CardAsyncService {

	Future<Boolean> cardLog(Long userId, Long cardId, String remarks);

	Future<Boolean> cardNotification(Cards card, Boolean pushToApp, Boolean pushToSms, Boolean pushToAppIos);
	
}