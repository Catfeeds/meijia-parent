package com.simi.service.op;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.op.AppCardType;
import com.simi.vo.po.AppCardTypeVo;

public interface AppCardTypeService {

	AppCardType initAppCardType();

	AppCardType selectByPrimaryKey(Long cardTypeId);

	int updateByPrimaryKeySelective(AppCardType record);

	int insertSelective(AppCardType record);
	
	int deleteByPrimaryKey(Long tId);

	PageInfo selectByListPage(int pageNo, int pageSize);

	List<AppCardType> selectByAppType(String appType);

	AppCardTypeVo getAppCardTypeVo(AppCardType item);

//	PageInfo searchVoListPage(AdSearchVo searchVo, int pageNo, int pageSize);



}
