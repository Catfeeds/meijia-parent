package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.user.UserDetailScoreService;
import com.simi.po.dao.user.UserDetailScoreMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.meijia.utils.TimeStampUtil;

@Service
public class UserDetailScoreServiceImpl implements UserDetailScoreService {

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserDetailScoreMapper userDetailScoreMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return userDetailScoreMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserDetailScore record) {
		return userDetailScoreMapper.insert(record);
	}

	@Override
	public int insert(Users users, UserDetailScore record) {
		usersMapper.updateByPrimaryKeySelective(users);
		return userDetailScoreMapper.insert(record);
	}

	@Override
	public int insertSelective(UserDetailScore record) {
		return userDetailScoreMapper.insertSelective(record);
	}

	@Override
	public List<UserDetailScore> selectByPage(String mobile, int page) {
		int start = 0;
		int end = Constants.PAGE_MAX_NUMBER;
		if (page > 1) {
			start = (page - 1) * Constants.PAGE_MAX_NUMBER;
//			end = page * Constants.PAGE_MAX_NUMBER;
		}
		return userDetailScoreMapper.selectByPage(mobile, start, end);
	}

	@Override
	public UserDetailScore initUserDetailScore() {
		UserDetailScore record = new UserDetailScore();
		record.setId(0L);
		record.setUserId(0L);
		record.setMobile("");
		record.setActionId((short) 0);
		record.setIsConsume((short) 0);
		record.setScore(0);
		record.setAddTime(TimeStampUtil.getNowSecond());
		return record;
	}

	@Override
	public UserDetailScore selectByPrimaryKey(Long id) {
		return userDetailScoreMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserDetailScore record) {
		
		return userDetailScoreMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<UserDetailScore> selectByUserIdPage(Long userId, int page) {
		int start = 0;
		int end = Constants.PAGE_MAX_NUMBER;
		if (page > 1) {
			start = (page - 1) * Constants.PAGE_MAX_NUMBER;
//			end = page * Constants.PAGE_MAX_NUMBER;
		}
		return userDetailScoreMapper.selectByUserIdPage(userId, start, end);
	}
}