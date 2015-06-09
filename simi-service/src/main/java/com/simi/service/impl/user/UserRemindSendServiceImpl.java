package com.simi.service.impl.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserRemindSendService;
import com.simi.po.dao.user.UserRemindSendMapper;
import com.simi.po.model.user.UserRemindSend;
@Service
public class UserRemindSendServiceImpl implements UserRemindSendService {
	@Autowired
	private UserRemindSendMapper userRemindSendMapper;
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userRemindSendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRemindSend record) {
		return userRemindSendMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRemindSend record) {
		return userRemindSendMapper.insertSelective(record);
	}

	@Override
	public UserRemindSend selectByPrimaryKey(Long id) {
		return userRemindSendMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRemindSend record) {
		return userRemindSendMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRemindSend record) {
		return userRemindSendMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByMap(Map<String, Object> map) {
		return userRemindSendMapper.deleteByMap(map);
	}

	@Override
	public List<UserRemindSend> selectByMobile(String mobile) {
		return userRemindSendMapper.selectByMobile(mobile);
	}

	@Override
	public List<UserRemindSend> queryByRemindId(Long remind_id) {
		return userRemindSendMapper.queryByRemindId(remind_id);
	}

}
