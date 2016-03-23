package com.simi.service.async;

import java.util.concurrent.Future;

import com.simi.po.model.user.Users;

public interface NoticeSmsAsyncService {

	Future<Boolean> noticeOrderPartner(Long orderId);

	Future<Boolean> noticeOrderOper(Long orderId);

	Future<Boolean> noticeOrderUser(Long orderId);

	Future<Boolean> noticeOrderSeniorOper(Long orderId);

	Future<Boolean> noticeOrderCardUser(Long orderId);

	Future<Boolean> noticeOrderCarUser(Long orderId);

	Boolean userOrderAmPushSms(Users users);

	Boolean userSecToUserPushSms(Users users);
	
	

	
}