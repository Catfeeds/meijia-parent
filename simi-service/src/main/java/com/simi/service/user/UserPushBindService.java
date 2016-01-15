package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserPushBind;

public interface UserPushBindService {

	int updateByPrimaryKeySelective(UserPushBind record);

	int insertSelective(UserPushBind record);
	
	UserPushBind selectByPrimaryKey(Long id);

	UserPushBind selectByUserId(Long userId);
	
	List<UserPushBind> selectByClientId(String clientId);

	List<UserPushBind> selectByUserIds(List<Long> userIds);

	int deleteByPrimaryKey(Long id);


}
