package com.simi.service.async;

import java.util.concurrent.Future;

public interface XcompanyAsyncService {

	Future<Boolean> checkin(Long id);

	Future<Boolean> checkinStatLate(Long companyId);

	Future<Boolean> checkinStatEarly(Long companyId);


}