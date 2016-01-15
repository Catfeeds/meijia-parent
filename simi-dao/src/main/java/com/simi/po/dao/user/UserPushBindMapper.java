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
    
    UserPushBind selectByUserId(Long userId);
    
    List<UserPushBind> selectByClientId(String clientId);

	List<UserPushBind> selectByUserIds(List<Long> userIds);
    
    
}