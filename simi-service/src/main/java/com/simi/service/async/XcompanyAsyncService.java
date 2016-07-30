package com.simi.service.async;

import java.util.concurrent.Future;

public interface XcompanyAsyncService {

	Future<Boolean> checkin(Long id);

	Future<Boolean> checkinStat(Long companyId, Long userId);


}