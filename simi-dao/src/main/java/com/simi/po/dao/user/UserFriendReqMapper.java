package com.simi.po.dao.user;

import com.simi.po.model.user.UserFriendReq;

public interface UserFriendReqMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriendReq record);

    int insertSelective(UserFriendReq record);

    UserFriendReq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriendReq record);

    int updateByPrimaryKey(UserFriendReq record);
}