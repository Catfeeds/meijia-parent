package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppTools;
import com.simi.vo.ApptoolsSearchVo;
import com.simi.vo.po.AppToolsVo;

public interface AppToolsService {

	AppTools initAppTools();

	AppTools selectByPrimaryKey(Long tId);

	int updateByPrimaryKeySelective(AppTools record);

	Long insertSelective(AppTools record);
	
	int deleteByPrimaryKey(Long tId);

	AppToolsVo getAppToolsVo(AppTools item, Long userId);

	String genQrCode(AppTools item);

	List<AppTools> selectBySearchVo(ApptoolsSearchVo searchVo);

	PageInfo selectByListPage(ApptoolsSearchVo searchVo, int pageNo, int pageSize);

}
