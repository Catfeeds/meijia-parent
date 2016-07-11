package com.simi.service.async;

import java.util.concurrent.Future;

import com.simi.po.model.user.Users;

public interface RecordAsyncService {

	Future<Boolean> avgRate(Short rateType, Long linkId);

	
}