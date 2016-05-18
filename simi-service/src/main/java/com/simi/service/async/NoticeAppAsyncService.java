package com.simi.service.async;

import java.util.concurrent.Future;

public interface NoticeAppAsyncService {

	Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent);

	Future<Boolean> pushMsgToExpr(Long orderId, String carNo, String carColor, String capImg, String remindContent);
	
}