package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserDetailScore;

public interface UserDetailScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDetailScore record);

    int insertSelective(UserDetailScore record);

    UserDetailScore selectByPrimaryKey(Long id);

    List<UserDetailScore> selectByPage(String mobile, int start, int end);

	int updateByPrimaryKeySelective(UserDetailScore record);
}