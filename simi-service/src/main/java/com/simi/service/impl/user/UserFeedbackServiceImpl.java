package com.simi.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserFeedbackService;
import com.simi.po.dao.user.UserFeedbackMapper;
import com.simi.po.model.user.UserFeedback;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {
	@Autowired
	private UserFeedbackMapper userFeedbackMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(UserFeedback record) {
		return userFeedbackMapper.insert(record);
	}

	@Override
	public int insertSelective(UserFeedback record) {
		return userFeedbackMapper.insertSelective(record);
	}

	@Override
	public UserFeedback selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(UserFeedback record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(UserFeedback record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
