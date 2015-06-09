package com.simi.service.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simi.common.Constants;
import com.simi.service.user.UserDetailScoreService;
import com.simi.po.dao.user.UserDetailScoreMapper;
import com.simi.po.dao.user.UsersMapper;
import com.simi.po.model.order.Orders;
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
	public UserDetailScore initUserDetailScore(String mobile, Orders orders, long now,
			UserDetailScore userDetailScore) {
				userDetailScore.setAddTime(now);
				userDetailScore.setUserId(orders.getUserId());
				userDetailScore.setMobile(mobile);
				return userDetailScore;
	}

	@Override
	public UserDetailScore selectByPrimaryKey(Long id) {
		return userDetailScoreMapper.selectByPrimaryKey(id);
	}

	@Override
	public UserDetailScore initUserDetailScore(Users users) {
		UserDetailScore userDetailScore = new UserDetailScore();
		userDetailScore.setUserId(users.getId());
		userDetailScore.setMobile(users.getMobile());
		userDetailScore.setScore(100);
		userDetailScore.setActionId(Constants.ACTION_CONVERT_SCORE);
		userDetailScore.setIsConsume(Constants.CONSUME_SCORE_USED);
		userDetailScore.setAddTime(TimeStampUtil.getNow()/1000);
		return userDetailScore;
	}
}