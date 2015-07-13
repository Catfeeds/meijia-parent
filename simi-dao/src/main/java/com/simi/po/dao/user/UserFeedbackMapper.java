package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.UserFeedback;

public interface UserFeedbackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFeedback record);

    int insertSelective(UserFeedback record);

    UserFeedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFeedback record);

    int updateByPrimaryKey(UserFeedback record);

	List<UserFeedback> selectByListPage();
}