package com.simi.po.dao.user;

import com.simi.po.model.user.UserPayStatus;

public interface UserPayStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPayStatus record);

    int insertSelective(UserPayStatus record);

    UserPayStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPayStatus record);

    int updateByPrimaryKey(UserPayStatus record);
}