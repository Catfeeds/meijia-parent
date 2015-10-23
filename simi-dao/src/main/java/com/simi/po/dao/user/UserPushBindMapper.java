package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserPushBind;

public interface UserPushBindMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPushBind record);

    int insertSelective(UserPushBind record);

    UserPushBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPushBind record);

    int updateByPrimaryKey(UserPushBind record);
    
    List<UserPushBind> selectByUserId(Long userId);
    
    UserPushBind selectByClientId(String clientId);

	UserPushBind selectByUserIdAndDeviceType(Long userId, String deviceType);
    
    
}