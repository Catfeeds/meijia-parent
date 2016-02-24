package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.UserAppTools;

public interface UserAppToolsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAppTools record);

    int insertSelective(UserAppTools record);

    UserAppTools selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAppTools record);

    int updateByPrimaryKey(UserAppTools record);

	List<UserAppTools> selectByUserIdAndStatus(Long userId);

	List<UserAppTools> selectByUserIdAndStatusOne(Long userId);
}