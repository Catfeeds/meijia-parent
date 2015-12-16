package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.AppTools;

public interface AppToolsMapper {
    int deleteByPrimaryKey(Long tId);

    int insert(AppTools record);

    int insertSelective(AppTools record);

    AppTools selectByPrimaryKey(Long tId);

    int updateByPrimaryKeySelective(AppTools record);

    int updateByPrimaryKey(AppTools record);

	List<AppTools> selectByListPage();

	List<AppTools> selectByAppType(String appType);
}