package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserActionRecordService;
import com.simi.vo.user.UserActionSearchVo;
import com.simi.po.dao.user.UserActionRecordMapper;
import com.simi.po.model.user.UserActionRecord;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserActionRecordServiceImpl implements UserActionRecordService {
	@Autowired
	private UserActionRecordMapper userActionRecordMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userActionRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserActionRecord record) {
		return userActionRecordMapper.insert(record);
	}

	@Override
	public int insertSelective(UserActionRecord record) {
		return userActionRecordMapper.insertSelective(record);
	}

	@Override
	public UserActionRecord selectByPrimaryKey(Long id) {
		return userActionRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(UserActionRecord record) {
		return userActionRecordMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserActionRecord record) {
		return userActionRecordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserActionRecord initUserActionRecord() {
		UserActionRecord record = new UserActionRecord();
		record.setId(0L);
		record.setUserId(0L);
		record.setActionType("");
		record.setParams("");
		record.setAddTime(TimeStampUtil.getNowSecond());

		return record;
	}
	
	@Override
	public List<UserActionRecord> selectBySearchVo(UserActionSearchVo searchVo) {
		return userActionRecordMapper.selectBySearchVo(searchVo);
	}

}