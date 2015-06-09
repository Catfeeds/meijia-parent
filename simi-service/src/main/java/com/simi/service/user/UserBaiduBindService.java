package com.simi.service.user;


import java.util.List;

import com.simi.po.model.user.UserBaiduBind;

public interface UserBaiduBindService {

	int updateByPrimaryKeySelective(UserBaiduBind record);

	int insertSelective(UserBaiduBind record);
	
	int totalBaiduBind();

	UserBaiduBind selectByPrimaryKey(Long id);

	UserBaiduBind selectByUserId(Long userId);

	List<UserBaiduBind> selectByOrdered();

	List<UserBaiduBind> selectByNotOrdered();

}
