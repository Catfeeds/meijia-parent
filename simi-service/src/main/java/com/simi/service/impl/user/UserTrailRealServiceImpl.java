package com.simi.service.impl.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.service.user.UserTrailRealService;
import com.simi.po.dao.user.UserTrailRealMapper;
import com.simi.po.model.user.UserTrailReal;

@Service
public class UserTrailRealServiceImpl implements UserTrailRealService{

	@Autowired
	private UserTrailRealMapper userTrailRealMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userTrailRealMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserTrailReal record) {
		return userTrailRealMapper.insert(record);
	}

	@Override
	public int insertSelective(UserTrailReal record) {
		return userTrailRealMapper.insertSelective(record);
	}

	@Override
	public UserTrailReal selectByPrimaryKey(Long id) {
		return userTrailRealMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserTrailReal record) {
		return userTrailRealMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserTrailReal record) {
		return userTrailRealMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserTrailReal initUserTrailReal() {
		UserTrailReal record = new UserTrailReal();
		
		record.setId(0L);
		record.setUserId(0L);
		record.setLatitude("");
		record.setLongitude("");
		record.setCity("");
		record.setPoiName("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}
	
	@Override
	public UserTrailReal selectByUserId(Long userId) {
		return userTrailRealMapper.selectByUserId(userId);
	}

}
