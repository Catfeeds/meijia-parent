package com.simi.po.dao.user;

import com.simi.po.model.user.UserLeavePass;

public interface UserLeavePassMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLeavePass record);

    int insertSelective(UserLeavePass record);

    UserLeavePass selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLeavePass record);

    int updateByPrimaryKey(UserLeavePass record);
}