package com.simi.service.impl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simi.service.user.UserCarService;

import com.simi.service.user.UsersService;

import com.simi.po.dao.user.UserCarMapper;

import com.simi.po.model.user.UserCar;

import com.simi.po.model.user.Users;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserCarServiceImpl implements UserCarService {

	@Autowired
	private UsersService userService;

	@Autowired
	private UserCarMapper userCarMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userCarMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserCar record) {
		return userCarMapper.insert(record);
	}

	@Override
	public int insertSelective(UserCar record) {
		return userCarMapper.insertSelective(record);
	}

	@Override
	public UserCar selectByPrimaryKey(Long id) {
		return userCarMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserCar record) {
		return userCarMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserCar record) {
		return userCarMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserCar initUserCar() {
		UserCar record = new UserCar();
		record.setId(0L);
		record.setUserId(0L);
		record.setCarNo("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}
	
	@Override
	public UserCar selectByUserId(Long userId) {
		return userCarMapper.selectByUserId(userId);
	}
	
	@Override
	public UserCar selectByCarNo(String carNo) {
		return userCarMapper.selectByCarNo(carNo);
	}
}