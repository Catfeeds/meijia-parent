package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserBaiduBindService;
import com.simi.po.dao.user.UserBaiduBindMapper;
import com.simi.po.model.user.UserBaiduBind;
@Service
public class UserBaiduBindServiceImpl implements UserBaiduBindService{

	@Autowired
	private UserBaiduBindMapper userBaiduBindMapper;

	@Override
	public UserBaiduBind selectByPrimaryKey(Long id) {
		return userBaiduBindMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(UserBaiduBind record) {
		return userBaiduBindMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int insertSelective(UserBaiduBind record) {
		return userBaiduBindMapper.insert(record);
	}
	@Override
	public UserBaiduBind selectByUserId(Long userId) {
		return userBaiduBindMapper.selectByUserId(userId);
	}
	
	/**
	 *  获取下过单的所有用户的channleId
	 */
	@Override
	public List<UserBaiduBind> selectByOrdered() {
		return userBaiduBindMapper.selectByOrdered();
	}
	
	/**
	 *  获取未下过单的所有用户的channleId
	 */
	@Override
	public List<UserBaiduBind> selectByNotOrdered() {
		return userBaiduBindMapper.selectByNotOrdered();
	}
	@Override
	public int totalBaiduBind() {
		return userBaiduBindMapper.totalBaiduBind();
	}	
}
