package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserLeavePassService;
import com.simi.service.user.UsersService;
import com.simi.po.dao.user.UserLeavePassMapper;
import com.simi.po.model.user.UserLeavePass;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserLeavePassServiceImpl implements UserLeavePassService {

	@Autowired
	private UsersService userService;
	
	@Autowired
	private UserLeavePassMapper userLeavePassMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userLeavePassMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserLeavePass record) {
		return userLeavePassMapper.insert(record);
	}

	@Override
	public int insertSelective(UserLeavePass record) {
		return userLeavePassMapper.insertSelective(record);
	}

	@Override
	public UserLeavePass selectByPrimaryKey(Long id) {
		return userLeavePassMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserLeavePass record) {
		return userLeavePassMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserLeavePass record) {
		return userLeavePassMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public UserLeavePass initUserLeavePass() {
		UserLeavePass record = new UserLeavePass();
		record.setId(0L);
		record.setLeaveId(0L);
		record.setCompanyId(0L);
		record.setUserId(0L);
		record.setPassUserId(0L);
		record.setPassStatus((short) 0);
		record.setRemarks("");
		record.setAddTime(TimeStampUtil.getNowSecond());
		record.setUpdateTime(TimeStampUtil.getNowSecond());
		return record;
	}	
	
	@Override
	public List<UserLeavePass> selectByLeaveId(Long leaveId) {
		return userLeavePassMapper.selectByLeaveId(leaveId);
	}	
				
}