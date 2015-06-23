package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.po.dao.user.UserRefSecMapper;
import com.simi.po.model.user.UserRefSec;
import com.simi.service.user.UserRefSecService;
import com.simi.vo.user.UserViewVo;

@Service
public class UserRefSecServiceImpl implements UserRefSecService{
  
	@Autowired
	private UserRefSecMapper userRefSecMapper;
	
	@Override
	public List<UserViewVo> selectVoByUserId(Long userId) {
		
		List<UserViewVo> list=userRefSecMapper.selectVoByUserId(userId);
		
		return list;
	}

	@Override
	public List<UserRefSec> selectBySecId(Long secId) {
		
		return userRefSecMapper.selectBySecId(secId);
	}

	@Override
	public UserRefSec selectByUserId(Long userId) {
		return userRefSecMapper.selectByUserId(userId);
	}

}
