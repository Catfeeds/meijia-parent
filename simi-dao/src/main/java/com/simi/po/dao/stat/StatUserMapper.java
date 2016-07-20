package com.simi.po.dao.stat;

import java.util.List;

import com.simi.po.model.stat.StatUser;

public interface StatUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(StatUser record);

    int insertSelective(StatUser record);

    StatUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(StatUser record);

    int updateByPrimaryKey(StatUser record);

	List<StatUser> selectByUserIds(List<Long> userIds);
}