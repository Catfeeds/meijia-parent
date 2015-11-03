package com.simi.service.async;

import java.util.concurrent.Future;

public interface UsersAsyncService {

	Future<Boolean> genImUser(Long userId);

	Future<Boolean> userLogined(Long userId, Short loginFrom, Long ip);

	Future<Boolean> userMobileCity(Long userId);

	Future<Boolean> newUserNotice(Long userId);
	
}