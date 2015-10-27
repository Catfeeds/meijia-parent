package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserPushBindService;
import com.simi.po.dao.user.UserPushBindMapper;
import com.simi.po.model.user.UserPushBind;
@Service
public class UserPushBindServiceImpl implements UserPushBindService{

	@Autowired
	private UserPushBindMapper userPushBindMapper;

	@Override
	public UserPushBind selectByPrimaryKey(Long id) {
		return userPushBindMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(UserPushBind record) {
		return userPushBindMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int insertSelective(UserPushBind record) {
		return userPushBindMapper.insert(record);
	}
	@Override
	public UserPushBind selectByUserId(Long userId) {
		return userPushBindMapper.selectByUserId(userId);
	}
	
	@Override
	public List<UserPushBind> selectByUserIds(List<Long> userIds) {
		return userPushBindMapper.selectByUserIds(userIds);
	}	
	
	@Override
	public UserPushBind selectByClientId(String clientId) {
		return userPushBindMapper.selectByClientId(clientId);
	}
	
}
