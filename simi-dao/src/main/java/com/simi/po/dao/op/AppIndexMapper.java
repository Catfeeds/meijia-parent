package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.AppIndex;

public interface AppIndexMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppIndex record);

    int insertSelective(AppIndex record);

    AppIndex selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppIndex record);

    int updateByPrimaryKey(AppIndex record);

	List<AppIndex> selectByListPage();

	List<AppIndex> selectByAppType(String appType);
}