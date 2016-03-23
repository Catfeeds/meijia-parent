package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserFriends;
import com.simi.vo.user.UserFriendSearchVo;

public interface UserFriendsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriends record);

    int insertSelective(UserFriends record);

    UserFriends selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriends record);

    int updateByPrimaryKey(UserFriends record);

	List<UserFriends> selectByListPage(UserFriendSearchVo searchVo);

	List<UserFriends> selectByUserId(Long userId);

	UserFriends selectByIsFirend(UserFriendSearchVo searchVo);
}