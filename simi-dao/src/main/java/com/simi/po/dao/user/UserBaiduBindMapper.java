package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserBaiduBind;

public interface UserBaiduBindMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBaiduBind record);

    int insertSelective(UserBaiduBind record);

    UserBaiduBind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBaiduBind record);

    int updateByPrimaryKey(UserBaiduBind record);

	UserBaiduBind selectByUserId(Long userId);
	
	List<UserBaiduBind> selectByOrdered();

	List<UserBaiduBind> selectByNotOrdered();
	
	int totalBaiduBind();
}