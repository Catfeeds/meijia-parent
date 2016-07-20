package com.simi.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.GroupUserMapper;
import com.simi.po.model.user.GroupUser;
import com.simi.service.user.GroupUserService;

@Service
public class GroupUserServiceImpl implements GroupUserService{
  
	@Autowired
	private GroupUserMapper groupUserMapper;

	/*
	 * 初始化用户对象
	 */
	@Override
	public GroupUser initGroupUser() {
		GroupUser record = new GroupUser();
		record.setId(0L);
		record.setGroupId(0L);
		record.setUserId(0L);
		record.setAddTime(TimeStampUtil.getNow() / 1000);
		return record;
	}	

	
	@Override
	public int insert(GroupUser record) {
		return groupUserMapper.insert(record);
	}

	@Override
	public int insertSelective(GroupUser record) {
		return groupUserMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(GroupUser record) {
		return groupUserMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public GroupUser selectByPrimaryKey(Long id) {
		return groupUserMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int totalByGroupId(Long groupId) {
		return groupUserMapper.totalByGroupId(groupId);
	}
	

}
