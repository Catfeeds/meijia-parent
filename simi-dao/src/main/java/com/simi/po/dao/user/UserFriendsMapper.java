package com.simi.po.dao.user;

import com.simi.po.model.user.UserFriends;

public interface UserFriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriends record);

    int insertSelective(UserFriends record);

    UserFriends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriends record);

    int updateByPrimaryKey(UserFriends record);
}