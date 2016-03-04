package com.simi.po.dao.user;

import com.simi.po.model.user.UserTrailReal;

public interface UserTrailRealMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserTrailReal record);

    int insertSelective(UserTrailReal record);

    UserTrailReal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTrailReal record);

    int updateByPrimaryKey(UserTrailReal record);

	UserTrailReal selectByUserId(Long userId);
}