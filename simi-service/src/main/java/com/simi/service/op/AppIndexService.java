package com.simi.service.op;


import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppIndex;

public interface AppIndexService {

	AppIndex initAppIndex();

	AppIndex selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AppIndex record);
	
	int insert(AppIndex record);

	int insertSelective(AppIndex record);
	
	int deleteByPrimaryKey(Long id);

	PageInfo selectByListPage(int pageNo, int pageSize);


}
