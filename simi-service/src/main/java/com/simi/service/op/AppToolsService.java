package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppTools;
import com.simi.vo.po.AppToolsVo;

public interface AppToolsService {

	AppTools initAppTools();

	AppTools selectByPrimaryKey(Long tId);

	int updateByPrimaryKeySelective(AppTools record);

	int insertSelective(AppTools record);
	
	int deleteByPrimaryKey(Long tId);

	PageInfo selectByListPage(int pageNo, int pageSize);

	List<AppTools> selectByAppType(String appType);

	AppToolsVo getAppToolsVo(AppTools item);

//	PageInfo searchVoListPage(AdSearchVo searchVo, int pageNo, int pageSize);



}
