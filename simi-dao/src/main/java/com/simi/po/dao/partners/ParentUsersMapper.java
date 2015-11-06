package com.simi.po.dao.partners;

import com.simi.po.model.partners.ParentUsers;

public interface ParentUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ParentUsers record);

    int insertSelective(ParentUsers record);

    ParentUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ParentUsers record);

    int updateByPrimaryKey(ParentUsers record);
}