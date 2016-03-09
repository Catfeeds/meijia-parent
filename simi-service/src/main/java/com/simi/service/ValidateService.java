package com.simi.service;

import com.simi.vo.AppResultData;


public interface ValidateService {

	AppResultData<Object> validateUser(Long userId);

	AppResultData<Object> validateOrderCity(Long userId);

	AppResultData<Object> validateUserAddr(Long userId, Long addrId);

	AppResultData<Object> validateFriend(Long userId, Long friendId);

	AppResultData<Object> validateSameCompany(Long userId, Long friendId);
	
	
}