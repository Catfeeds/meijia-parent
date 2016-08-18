package com.simi.service.impl.stat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.stat.StatUserService;
import com.simi.service.user.UsersService;
import com.simi.po.model.stat.StatUser;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.stat.StatUserMapper;

@Service
public class StatUserServiceImpl implements StatUserService {
	@Autowired
	StatUserMapper statUserMapper;

	@Autowired
	UsersService usersService;

	@Override
	public StatUser initStatUser() {
		StatUser record = new StatUser();
		record.setUserId(0L);
		record.setTotalCards(0);
		record.setTotalCompanys(0);
		record.setTotalFeeds(0);
		record.setTotalOrders(0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public StatUser selectByPrimaryKey(Long id) {
		return statUserMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(StatUser record) {
		return statUserMapper.insertSelective(record);
	}
	
	@Override
	public int insert(StatUser record) {
		return statUserMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(StatUser record) {
		return statUserMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(StatUser record) {
		return statUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return statUserMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<StatUser> selectByUserIds(List<Long> userIds) {
		return statUserMapper.selectByUserIds(userIds);
	}
	
}