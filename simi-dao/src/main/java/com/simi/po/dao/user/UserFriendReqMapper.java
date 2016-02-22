package com.simi.po.dao.user;
import java.util.List;

import com.simi.po.model.user.UserFriendReq;
import com.simi.vo.UserFriendSearchVo;

public interface UserFriendReqMapper {
    int insert(UserFriendReq record);
    
    int insertBySelective(UserFriendReq record);

    int deleteByPrimaryKey(Long id);

    int insertSelective(UserFriendReq record);

    UserFriendReq selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriendReq record);

    int updateByPrimaryKey(UserFriendReq record);

	UserFriendReq selectByIsFirend(UserFriendSearchVo searchVo);

	List<UserFriendReq> selectByUserId(Long userId);

}