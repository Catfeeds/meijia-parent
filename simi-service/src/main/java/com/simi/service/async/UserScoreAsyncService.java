package com.simi.service.async;

import java.util.concurrent.Future;

public interface UserScoreAsyncService {

	Future<Boolean> sendScore(Long userId, Integer score, String action, String params, String remarks);

	Future<Boolean> sendScoreCompany(Long userId, Long companyId);

	Future<Boolean> sendScoreCard(Long userId, Long cardId, Short cardType);

	Future<Boolean> consumeScore(Long userId, Integer score, String action, String params, String remarks);

	Future<Boolean> sendScoreFeedAdd(Long userId, Long fid);

	Future<Boolean> sendScoreFeedComment(Long userId, Long commentId);

	Future<Boolean> sendScoreFeedCaiNa(Long userId, int score, Long commentId);


}
