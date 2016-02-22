package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserFriendReq;
import com.simi.vo.UserFriendSearchVo;
import com.simi.vo.user.UserFriendReqVo;

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

	UserFriendReqVo getFriendReqVo(UserFriendReq item);

	/*List<UserMsg> selectByUserId(Long userId);

	List<UserMsg> selectBySearchVo(UserMsgSearchVo searchVo);

	PageInfo selectByListPage(UserMsgSearchVo searchVo, int pageNo, int pageSize);*/

}