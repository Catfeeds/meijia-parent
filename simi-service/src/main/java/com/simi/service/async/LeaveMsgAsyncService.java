package com.simi.service.async;

import java.util.concurrent.Future;

public interface LeaveMsgAsyncService {

	Future<Boolean> newLeaveMsg(Long userId, Long leaveId);

	Future<Boolean> newLeavePassMsg(Long userId, Long leaveId);

	Future<Boolean> newLeaveCancelMsg(Long leaveId);

	
}