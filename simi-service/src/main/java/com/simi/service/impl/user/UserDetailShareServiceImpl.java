package com.simi.service.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.service.user.UserDetailShareService;
import com.simi.po.dao.user.UserDetailScoreMapper;
import com.simi.po.dao.user.UserDetailShareMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.UserDetailShare;
import com.simi.po.model.user.Users;

@Service
public class UserDetailShareServiceImpl implements UserDetailShareService{

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserDetailShareMapper userDetailShareMapper;

	@Autowired
	private UserDetailScoreMapper userDetailScoreMapper;

	@Override
	public int insert(UserDetailShare record) {
		return userDetailShareMapper.insert(record);
	}

	@Override
	public int insertSelective(UserDetailShare record) {
		return userDetailShareMapper.insertSelective(record);
	}

	@Override
	public UserDetailShare selectByMobile(String mobile) {
		return userDetailShareMapper.selectByMobile(mobile);
	}

	@Override
	public int updateByPrimaryKey(UserDetailShare record) {
		return userDetailShareMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(UserDetailShare record) {
		return userDetailShareMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insert(Users users, UserDetailShare userDetailShare,
			UserDetailScore userDetailScore) {
		int flag = usersMapper.updateByPrimaryKeySelective(users);
		if (flag > 0 && userDetailShareMapper.insert(userDetailShare) > 0) {
			return userDetailScoreMapper.insert(userDetailScore);
		}
		return 0;
	}

	@Override
	public List<UserDetailShare> selectByMobileAndShareType(Long userId,String shareType) {
		Map<String,Object> conditions = new HashMap<String, Object>();
		if((userId !=null ) && (shareType!=null && !shareType.isEmpty())){
			conditions.put("userId", userId);
			conditions.put("shareType", shareType.trim());
		}
		return userDetailShareMapper.selectByMobileAndShareType(conditions);
	}
}