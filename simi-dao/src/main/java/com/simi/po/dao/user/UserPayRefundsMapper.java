package com.simi.po.dao.user;

import com.simi.po.model.user.UserPayRefunds;

public interface UserPayRefundsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPayRefunds record);

    int insertSelective(UserPayRefunds record);

    UserPayRefunds selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPayRefunds record);

    int updateByPrimaryKey(UserPayRefunds record);
}