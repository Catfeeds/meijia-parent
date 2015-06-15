package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.model.user.UserRef3rd;
import com.simi.service.user.UserRef3rdService;

@Service
public class UserRef3rdServiceImpl implements UserRef3rdService {

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return userRef3rdMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRef3rd record) {
		return userRef3rdMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRef3rd record) {
		return userRef3rdMapper.insertSelective(record);
	}

	@Override
	public UserRef3rd selectByPrimaryKey(Long id) {
		return userRef3rdMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRef3rd record) {
		return userRef3rdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRef3rd record) {
		return userRef3rdMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserRef3rd selectByMobile(String mobile) {
		return userRef3rdMapper.selectByMobile(mobile);
	}

	@Override
	public UserRef3rd selectByUserId(Long userId) {
		return userRef3rdMapper.selectByUserId(userId);
	}

	@Override
	public UserRef3rd selectByPidAnd3rdType(String pid, String thirdType) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		if((pid!=null && !pid.isEmpty()) && (thirdType!=null && !thirdType.isEmpty())){
			conditions.put("refPrimaryKey",pid);
			conditions.put("refType",thirdType);
		}
		return userRef3rdMapper.selectByPidAnd3rdType(conditions);
	}

	@Override
	public UserRef3rd initUserRef3rd(String mobile) {
		UserRef3rd userRef3rd = new UserRef3rd();
		userRef3rd.setMobile(mobile);
		userRef3rd.setPassword(" ");
		userRef3rd.setRefPrimaryKey(" ");
		userRef3rd.setRefType(" ");
		userRef3rd.setUserId((long)0);
		userRef3rd.setUsername(" ");
		userRef3rd.setAddTime(TimeStampUtil.getNow()/1000);
		return userRef3rd;
	}

}
