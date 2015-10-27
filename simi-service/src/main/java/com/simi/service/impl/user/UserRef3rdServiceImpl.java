package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meijia.utils.StringUtil;
import com.meijia.utils.TimeStampUtil;
import com.simi.po.dao.user.UserRef3rdMapper;
import com.simi.po.model.user.UserRef3rd;
import com.simi.po.model.user.UserRefSec;
import com.simi.po.model.user.Users;
import com.simi.service.user.UserRef3rdService;
import com.simi.service.user.UserRefSecService;
import com.simi.service.user.UsersService;

@Service
public class UserRef3rdServiceImpl implements UserRef3rdService {

	@Autowired
	private UserRef3rdMapper userRef3rdMapper;
	
	@Autowired
	private UserRefSecService userRefSecService;
	
	@Autowired
	private UsersService usersService;	
	
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
		
		UserRef3rd userRef3rd = userRef3rdMapper.selectByUserIdForIm(userId);
		
		if (userRef3rd == null) {
			Users u =  usersService.getUserById(userId);
			usersService.genImUser(u);
		}
		
		userRef3rd = userRef3rdMapper.selectByUserIdForIm(userId);
		
		return userRef3rd;
	}
	
	@Override
	public List<UserRef3rd> selectByUserIds(List<Long> userIds) {
		return userRef3rdMapper.selectByUserIds(userIds);
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
	public UserRef3rd selectByUserNameAnd3rdType(String userName, String thirdType) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(thirdType)){
			conditions.put("userName",userName);
			conditions.put("refType",thirdType);
		}
		return userRef3rdMapper.selectByUserNameAnd3rdType(conditions);
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
		UserRefSec record = userRefSecService.selectByUserId(userId);
		if (record != null) {
			return true;
		}

		Long adminId = 0L;

		List<HashMap> statBySenior = userRefSecService.statBySecId();

		if (statBySenior == null || statBySenior.size() <= 0) {
			return false;
		}
		String secId = "";
		for (int i =0; i < statBySenior.size(); i++) {
			secId = statBySenior.get(i).get("sec_id").toString();
			if (StringUtil.isEmpty(secId)) continue;
			
			adminId = Long.valueOf(secId);
			
			//注意分配秘书不能给自己分配.
			if (adminId.equals(userId)) {
				continue;
			} else {
				break;
			}
		}
		
		record = userRefSecService.initUserRefSec();
		record.setUserId(userId);
		record.setSecId(adminId);

		userRefSecService.insertSelective(record);
		return true;
	}

}
