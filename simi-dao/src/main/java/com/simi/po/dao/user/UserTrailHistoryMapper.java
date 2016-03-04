package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserTrailHistory;

public interface UserTrailHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserTrailHistory record);

    int insertSelective(UserTrailHistory record);

    UserTrailHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserTrailHistory record);

    int updateByPrimaryKey(UserTrailHistory record);

	List<UserTrailHistory> selectByUserId(Long userId);
}