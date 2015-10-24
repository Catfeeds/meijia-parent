package com.simi.service.impl.user;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.user.TagUsersMapper;
import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.service.user.TagsUsersService;
@Service
public class TagsUsersServiceImpl implements TagsUsersService {


	@Autowired
	private TagUsersMapper tagUsersMapper;

	@Override
	public int insertByTagUsers(TagUsers tagUsers) {
		
		return tagUsersMapper.insertSelective(tagUsers);
	}
	
	@Override
	public List<TagUsers> selectByUserIds(List<Long> userIds) {
		return tagUsersMapper.selectByUserIds(userIds);
	}

	@Override
	public List<TagUsers> selectByUserId(Long id) {

		return tagUsersMapper.selectByUserId(id);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		
		return tagUsersMapper.deleteByPrimaryKey(id);
	}


	
}