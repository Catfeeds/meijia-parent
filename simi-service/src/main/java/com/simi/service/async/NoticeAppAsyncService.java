package com.simi.service.async;

import java.util.concurrent.Future;

public interface NoticeAppAsyncService {

	Future<Boolean> pushMsgToExpr(Long orderId, String carNo, String carColor, String capImg, String remindContent);

	Future<Boolean> pushMsgToDevice(Long userId, String msgTitle, String msgContent, String category, String action, String params, String gotoUrl);
	
}