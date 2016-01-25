package com.simi.po.dao.user;

import com.simi.po.model.user.UserMsg;

public interface UserMsgMapper {
    int deleteByPrimaryKey(Long msgId);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectByPrimaryKey(Long msgId);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKey(UserMsg record);
}