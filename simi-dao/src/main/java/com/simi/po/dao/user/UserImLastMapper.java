package com.simi.po.dao.user;

import com.simi.po.model.user.UserImLast;

public interface UserImLastMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserImLast record);

    int insertSelective(UserImLast record);

    UserImLast selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserImLast record);

    int updateByPrimaryKey(UserImLast record);
}