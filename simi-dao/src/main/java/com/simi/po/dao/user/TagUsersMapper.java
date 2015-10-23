package com.simi.po.dao.user;

import java.util.List;

import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;

public interface TagUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagUsers record);

    int insertSelective(TagUsers record);

    TagUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TagUsers record);

    int updateByPrimaryKey(TagUsers record);

	List<TagUsers> selectByUserIds(List<Long> userIds);
}