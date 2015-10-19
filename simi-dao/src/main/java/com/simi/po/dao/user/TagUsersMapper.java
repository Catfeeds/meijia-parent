package com.simi.po.dao.user;

import com.simi.po.model.user.TagUsers;

public interface TagUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagUsers record);

    int insertSelective(TagUsers record);

    TagUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TagUsers record);

    int updateByPrimaryKey(TagUsers record);
}