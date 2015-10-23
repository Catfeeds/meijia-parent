package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserPushBind;

public interface UserPushBindService {

	int updateByPrimaryKeySelective(UserPushBind record);

	int insertSelective(UserPushBind record);
	
	UserPushBind selectByPrimaryKey(Long id);

	List<UserPushBind> selectByUserId(Long userId);
	
	UserPushBind selectByClientId(String clientId);

	UserPushBind selectByUserIdAndDeviceType(Long userId, String deviceType);


}
