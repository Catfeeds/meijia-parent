package com.simi.service.async;

import java.util.concurrent.Future;

public interface XcompanyAsyncService {

	Future<Boolean> checkin(Long id);

	Future<Boolean> checkinStatLate(Long companyId);

	Future<Boolean> checkinStatEarly(Long companyId);

	Future<Boolean> checkinStatLeave(Long leaveId);

	Future<Boolean> checkinNotice(Long companyId);


}