package com.simi.service.user;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.user.UserFriendReq;
import com.simi.vo.user.UserFriendReqVo;
import com.simi.vo.user.UserFriendSearchVo;

public interface UserFriendReqService {

	int deleteByPrimaryKey(Long id);

	int insert(UserFriendReq record);

	int insertSelective(UserFriendReq record);

	UserFriendReq selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserFriendReq record);

	int updateByPrimaryKeySelective(UserFriendReq record);

	UserFriendReq initUserFriendReq();

	UserFriendReq selectByIsFirend(UserFriendSearchVo searchVo);

	List<UserFriendReq> selectByUserId(Long userId);

	PageInfo selectByListPage(UserFriendSearchVo searchVo, int pageNo, int pageSize);

	UserFriendReqVo getFriendReqVo(UserFriendReq item, Long userId);

}