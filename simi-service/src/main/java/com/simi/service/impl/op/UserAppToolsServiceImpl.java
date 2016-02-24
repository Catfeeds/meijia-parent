package com.simi.service.impl.op;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.op.UserAppToolsService;
import com.simi.po.dao.op.UserAppToolsMapper;
import com.simi.po.model.op.UserAppTools;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserAppToolsServiceImpl implements UserAppToolsService {

	@Autowired
	private UserAppToolsMapper userAppToolsMapper;

	@Override
	public UserAppTools initUserAppTools() {

		UserAppTools record = new UserAppTools();
		    record.setId(0L);
		    record.setUserId(0L);
		    record.settId(0L);
		    record.setStatus((short)0);
		    record.setAddTime(TimeStampUtil.getNow()/1000);
			return record;
		}

	@Override
	public UserAppTools selectByPrimaryKey(Long id) {
		
		return userAppToolsMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(UserAppTools record) {
		
		return userAppToolsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(UserAppTools record) {
		
		return userAppToolsMapper.insertSelective(record);
	}
	
	@Override
	public int insert(UserAppTools record) {
		
		return userAppToolsMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(Long tId) {

		return userAppToolsMapper.deleteByPrimaryKey(tId);
	}

}
