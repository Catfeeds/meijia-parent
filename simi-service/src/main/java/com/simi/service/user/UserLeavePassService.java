package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserLeave;
import com.simi.po.model.user.UserLeavePass;


public interface UserLeavePassService {

	int deleteByPrimaryKey(Long id);

	int insert(UserLeavePass record);

	int insertSelective(UserLeavePass record);

	UserLeavePass selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserLeavePass record);

	int updateByPrimaryKeySelective(UserLeavePass record);

	UserLeavePass initUserLeavePass();

	List<UserLeavePass> selectByLeaveId(Long leaveId);
	
}