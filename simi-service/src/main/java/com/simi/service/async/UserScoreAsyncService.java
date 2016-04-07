package com.simi.service.async;

import java.util.concurrent.Future;

public interface UserScoreAsyncService {

	Future<Boolean> sendScore(Long userId, Integer score, String action, String params, String remarks);

}
