package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.TotalCompany;
import com.simi.vo.xcloud.CompanySearchVo;

public interface TotalCompanyService {

	TotalCompany initTotalCompany();

	int deleteByPrimaryKey(Long id);

	int insertSelective(TotalCompany record);
	
	int updateByPrimaryKeySelective(TotalCompany record);

	TotalCompany selectByPrimarykey(Long id);

	List<TotalCompany> selectBySearchVo(CompanySearchVo searchVo);
	
}
