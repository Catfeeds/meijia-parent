package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.GroupUser;

public interface GroupUserService {
	
	GroupUser initGroupUser();

	int insert(GroupUser record);

	int insertSelective(GroupUser record);

	int updateByPrimaryKeySelective(GroupUser record);
	
	GroupUser selectByPrimaryKey(Long id);

	int totalByGroupId(Long groupId);

	List<GroupUser> selectByUserIds(List<Long> userIds);

	int deleteByUserId(Long userId);

}
