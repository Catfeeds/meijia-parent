package com.simi.service.impl.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.service.user.UserTrailHistoryService;
import com.simi.po.dao.user.UserTrailHistoryMapper;
import com.simi.po.model.user.UserTrailHistory;

@Service
public class UserTrailHistoryServiceImpl implements UserTrailHistoryService{

	@Autowired
	private UserTrailHistoryMapper userTrailHistoryMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userTrailHistoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserTrailHistory record) {
		return userTrailHistoryMapper.insert(record);
	}

	@Override
	public int insertSelective(UserTrailHistory record) {
		return userTrailHistoryMapper.insertSelective(record);
	}

	@Override
	public UserTrailHistory selectByPrimaryKey(Long id) {
		return userTrailHistoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserTrailHistory record) {
		return userTrailHistoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserTrailHistory record) {
		return userTrailHistoryMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserTrailHistory initUserTrailHistory() {
		UserTrailHistory record = new UserTrailHistory();
		
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
	public List<UserTrailHistory> selectByUserId(Long userId) {
		return userTrailHistoryMapper.selectByUserId(userId);
	}

}
