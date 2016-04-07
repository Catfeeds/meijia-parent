package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.Xcompany;
import com.simi.vo.xcloud.CompanySearchVo;

public interface XCompanyService {

	int deleteByPrimaryKey(Long id);

	Long insert(Xcompany record);

	int insertSelective(Xcompany record);

	int updateByPrimaryKey(Xcompany record);

	int updateByPrimaryKeySelective(Xcompany record);

	Xcompany initXcompany();
	
	Xcompany selectByPrimaryKey(Long id);

	List<Xcompany> selectByIds(List<Long> ids);

	List<Xcompany> selectByListPage(CompanySearchVo searchVo, int pageNo, int pageSize);

	List<Xcompany> selectBySearchVo(CompanySearchVo searchVo);

}
