package com.simi.service.impl.async;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.simi.po.model.user.UserDetailScore;
import com.simi.po.model.user.Users;
import com.simi.service.async.UserScoreAsyncService;
import com.simi.service.user.UserDetailScoreService;
import com.simi.service.user.UsersService;

@Service
public class UserScoreAsyncServiceImpl implements UserScoreAsyncService {

	@Autowired
	public UsersService usersService;
	
	@Autowired
	public UserDetailScoreService userDetailScoreService;
	
	/**
	 * 异步积分明细记录
	 */
	@Async
	@Override
	public Future<Boolean> sendScore(Long userId, Integer score, String action, String params, String remarks) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		//记录积分明细
		UserDetailScore record = userDetailScoreService.initUserDetailScore();
		record.setUserId(userId);
		record.setMobile(u.getMobile());
		record.setScore(score);
		record.setAction(action);
		record.setParams(params);
		record.setRemarks(remarks);
		
		userDetailScoreService.insert(record);
		
		//更新总积分
		u.setScore(u.getScore() + score);
		usersService.updateByPrimaryKeySelective(u);
		
		return new AsyncResult<Boolean>(true);
	}
	
	/**
	 * 异步积分明细记录，判断每日上限
	 */
	@Async
	@Override
	public Future<Boolean> sendScoreLimitCompany(Long userId, Integer score, String action, String params, String remarks) {
		
		Users u = usersService.selectByPrimaryKey(userId);
		
		if (u == null) return new AsyncResult<Boolean>(true);
		
		String mobile = u.getMobile();
		
		//记录积分明细
		UserDetailScore record = userDetailScoreService.initUserDetailScore();
		record.setUserId(userId);
		record.setMobile(u.getMobile());
		record.setScore(score);
		record.setAction(action);
		record.setParams(params);
		record.setRemarks(remarks);
		
		userDetailScoreService.insert(record);
		
		//更新总积分
		u.setScore(u.getScore() + score);
		usersService.updateByPrimaryKeySelective(u);
		
		return new AsyncResult<Boolean>(true);
	}


}
