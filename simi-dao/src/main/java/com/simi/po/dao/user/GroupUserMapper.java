package com.simi.po.dao.user;

import com.simi.po.model.user.GroupUser;

public interface GroupUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);

	int totalByGroupId(Long groupId);
}