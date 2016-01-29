package com.simi.service.op;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppHelp;

public interface AppHelpService {

	AppHelp initAppHelp();

	AppHelp selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AppHelp record);
	
	int insert(AppHelp record);

	int insertSelective(AppHelp record);
	
	int deleteByPrimaryKey(Long id);

	PageInfo selectByListPage(int pageNo, int pageSize);

	AppHelp selectByAction(String action);

}
