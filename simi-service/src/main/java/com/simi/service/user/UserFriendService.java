package com.simi.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserFriends;
import com.simi.po.model.user.Users;
import com.simi.vo.AppResultData;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.user.UserFriendViewVo;

public interface UserFriendService {

	int deleteByPrimaryKey(Long id);

	int insert(UserFriends record);

	int insertSelective(UserFriends record);

	UserFriends selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserFriends record);

	int updateByPrimaryKeySelective(UserFriends record);

	UserFriends initUserFriend();

	PageInfo selectByListPage(UserFriendSearchVo searchVo, int pageNo, int pageSize);

	List<UserFriends> selectByUserId(Long userId);

	UserFriends selectByIsFirend(UserFriendSearchVo searchVo);

	Boolean addFriends(Users u, Users friendUser);

	List<UserFriendViewVo> changeToUserFriendViewVos(List<UserFriends> userFriends);

	AppResultData<Object> addFriendReq(Users u, Users friendUser);

}