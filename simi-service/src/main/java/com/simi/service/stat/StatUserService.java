package com.simi.service.stat;

import java.util.List;

import com.simi.po.model.stat.StatUser;

public interface StatUserService {

	StatUser selectByPrimaryKey(Long id);

	int insertSelective(StatUser record);

	int updateByPrimaryKey(StatUser record);

	int updateByPrimaryKeySelective(StatUser record);

	int deleteByPrimaryKey(Long id);

	int insert(StatUser record);

	StatUser initStatUser();

	List<StatUser> selectByUserIds(List<Long> userIds);

}
