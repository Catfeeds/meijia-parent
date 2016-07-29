package com.simi.service.async;

import java.util.concurrent.Future;

public interface RecordAsyncService {

	Future<Boolean> avgRate(Short rateType, Long linkId);

	
}