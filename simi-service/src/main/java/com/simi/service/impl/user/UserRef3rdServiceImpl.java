package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.sec.SecRef3rdMapper;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.dao.user.UserRefSecMapper;
import com.simi.po.dao.user.UserRefSeniorMapper;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.UserRefSenior;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserRef3rdService;

@Service
public class UserRef3rdServiceImpl implements UserRef3rdService {

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;
	
	@Autowired
	private UserRefSecMapper userRefSecMapper;
	
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
	public UserRef3rd selectByUserIdForIm(Long userId) {
		return userRef3rdMapper.selectByUserIdForIm(userId);
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
	
	/**
	 * 为第三方登录的用户分配秘书
	 */
	@Override
	public Boolean allotSec(Users user) {
		
		Long userId = user.getId();
		//如果之前用户已经分配过秘书，则不需要再分配
		UserRefSec record = userRefSecMapper.selectByUserId(userId);
		if (record != null) {
			return true;
		}

		Long adminId = 0L;

		List<HashMap> statBySenior = userRefSecMapper.statBySecId();

		if (statBySenior == null || statBySenior.size() <= 0) {
			return false;
		}
		String secId = statBySenior.get(0).get("sec_id").toString();
	
		adminId = Long.valueOf(secId);
		record = this.initUserRefSec();
		record.setUserId(userId);
		record.setSecId(adminId);

		userRefSecMapper.insertSelective(record);
		return true;
	}

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
}
