package com.simi.service.user;

import com.simi.po.model.user.GroupUser;

public interface GroupUserService {
	
	GroupUser initGroupUser();

	int insert(GroupUser record);

	int insertSelective(GroupUser record);

	int updateByPrimaryKeySelective(GroupUser record);
	
	GroupUser selectByPrimaryKey(Long id);

	int totalByGroupId(Long groupId);

}
