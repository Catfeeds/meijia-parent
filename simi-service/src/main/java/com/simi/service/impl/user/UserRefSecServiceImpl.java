package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserRefSecMapper;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.UserRefSenior;
import com.simi.service.user.UserRefSecService;
import com.simi.vo.user.UserViewVo;

@Service
public class UserRefSecServiceImpl implements UserRefSecService{
  
	@Autowired
	private UserRefSecMapper userRefSecMapper;
	
	/*
	 * 初始化用户对象
	 */
	@Override
	public UserRefSec initUserRefSec() {
		UserRefSec record = new UserRefSec();
		record.setId(0L);
		record.setUserId(0L);
		record.setSecId(0L);
		record.setAddTime(TimeStampUtil.getNow() / 1000);
		return record;
	}	
	
	@Override
	public List<UserViewVo> selectVoByUserId(Long userId) {
		
		List<UserViewVo> list=userRefSecMapper.selectVoByUserId(userId);
		
		return list;
	}


	@Override
	public UserRefSec selectByUserId(Long userId) {
		return userRefSecMapper.selectByUserId(userId);
	}


	@Override
	public List<UserRefSec> selectBySecId(Long secId) {
		
		return userRefSecMapper.selectBySecId(secId);
	}
	
	@Override
	public List<HashMap> statBySecId() {
		return userRefSecMapper.statBySecId();
	}
	
	@Override
	public int insert(UserRefSec record) {
		return userRefSecMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRefSec record) {
		return userRefSecMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(UserRefSec record) {
		return userRefSecMapper.updateByPrimaryKeySelective(record);
	}
	



}
