package com.simi.service.impl.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserTrailService;
import com.simi.po.dao.user.UserTrailMapper;
import com.simi.po.model.user.UserTrail;

@Service
public class UserTrailServiceImpl implements UserTrailService{

	@Autowired
	private UserTrailMapper userTrailMapper;
	@Override
	public int insertSelective(UserTrail record) {

		return userTrailMapper.insertSelective(record);

	}

}
