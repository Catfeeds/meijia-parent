package com.simi.po.dao.user;

import com.simi.po.model.user.UserFans;

public interface UserFansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    UserFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);
}