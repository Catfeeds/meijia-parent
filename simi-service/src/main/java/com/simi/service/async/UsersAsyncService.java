package com.simi.service.async;

import java.util.concurrent.Future;

import com.simi.po.model.user.Users;

public interface UsersAsyncService {

	Future<Boolean> genImUser(Long userId);

	Future<Boolean> userLogined(Long userId, Short loginFrom, Long ip);

	Future<Boolean> userMobileCity(Long userId);

	Future<Boolean> newUserNotice(Long userId);

	Future<Boolean> addFriends(Users u, Users friendUser);

	Future<Boolean> addDefaultFriends(Long userId);

	Future<Boolean> statUserInit(Long userId);

	Future<Boolean> statUser(Long userId, String statType);
	
}