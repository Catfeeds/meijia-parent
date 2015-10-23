package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;


public interface TagsUsersService {

	int insertByTagUsers(TagUsers tagUsers);

	List<TagUsers> selectByUserIds(List<Long> userIds);

	

}