package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;


public interface TagsUsersService {

	int insertByTagUsers(TagUsers tagUsers);
	
	int insert(TagUsers record);

	List<TagUsers> selectByUserIds(List<Long> userIds);

	List<TagUsers> selectByUserId(Long id);
	
	int deleteByPrimaryKey(Long id);

	int deleteByUserId(Long userId);

	
	
	

	

}