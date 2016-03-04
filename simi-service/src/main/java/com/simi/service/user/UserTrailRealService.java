package com.simi.service.user;

import com.simi.po.model.user.UserTrailReal;

public interface UserTrailRealService {

	int insertSelective(UserTrailReal record);

	int deleteByPrimaryKey(Long id);

	int insert(UserTrailReal record);

	UserTrailReal selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserTrailReal record);

	int updateByPrimaryKeySelective(UserTrailReal record);

	UserTrailReal initUserTrailReal();

	UserTrailReal selectByUserId(Long userId);

}
