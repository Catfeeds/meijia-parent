package com.simi.po.dao.user;

import com.simi.po.model.user.UserImHistory;

public interface UserImHistoryMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(UserImHistory record);

    int insertSelective(UserImHistory record);

    int updateByPrimaryKeySelective(UserImHistory record);

    int updateByPrimaryKey(UserImHistory record);

    UserImHistory selectByPrimaryKey(Long id);
    
    UserImHistory selectByUuid(String uuid);
}