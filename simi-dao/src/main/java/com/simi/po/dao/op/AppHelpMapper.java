package com.simi.po.dao.op;

import java.util.List;

import com.simi.po.model.op.AppHelp;

public interface AppHelpMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AppHelp record);

    int insertSelective(AppHelp record);

    AppHelp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppHelp record);

    int updateByPrimaryKey(AppHelp record);

	List<AppHelp> selectByListPage();
}