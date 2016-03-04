package com.simi.service.user;

import java.util.List;

import com.simi.po.model.user.UserTrailHistory;

public interface UserTrailHistoryService {

	int insertSelective(UserTrailHistory record);

	int deleteByPrimaryKey(Long id);

	int insert(UserTrailHistory record);

	UserTrailHistory selectByPrimaryKey(Long id);

	int updateByPrimaryKey(UserTrailHistory record);

	int updateByPrimaryKeySelective(UserTrailHistory record);

	UserTrailHistory initUserTrailHistory();

	List<UserTrailHistory> selectByUserId(Long userId);

}
