package com.simi.po.dao.op;

import com.simi.po.model.op.UserAppTools;

public interface UserAppToolsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAppTools record);

    int insertSelective(UserAppTools record);

    UserAppTools selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAppTools record);

    int updateByPrimaryKey(UserAppTools record);
}