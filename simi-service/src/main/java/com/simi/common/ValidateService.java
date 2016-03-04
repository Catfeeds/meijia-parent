package com.simi.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.simi.po.model.user.Tags;
import com.simi.service.order.OrdersService;
import com.simi.service.user.UserTrailRealService;
import com.simi.service.user.UsersService;
import com.simi.vo.AppResultData;


public interface ValidateService {

	AppResultData<Object> validateUser(Long userId);

	AppResultData<Object> validateOrderCity(Long userId);

	AppResultData<Object> validateUserAddr(Long userId, Long addrId);
	
	
}