package com.simi.service.async;

import java.util.concurrent.Future;

public interface UserScoreAsyncService {

	Future<Boolean> sendScore(Long userId, Integer score, String action, String params, String remarks);

	Future<Boolean> sendScoreCompany(Long userId, Long companyId);

	Future<Boolean> sendScoreCard(Long userId, Long cardId, Short cardType);


}
