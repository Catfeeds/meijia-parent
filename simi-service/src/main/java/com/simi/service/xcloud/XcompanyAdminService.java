package com.simi.service.xcloud;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.vo.xcloud.CompanyAdminSearchVo;

public interface XcompanyAdminService {

	XcompanyAdmin initXcompanyAdmin();

	int deleteByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(XcompanyAdmin XcompanyAdmin);

	XcompanyAdmin selectByPrimarykey(Long id);

	@SuppressWarnings("rawtypes")
	PageInfo selectByListPage(CompanyAdminSearchVo searchVo, int pageNo, int pageSize);
	
	List<XcompanyAdmin> selectBySearchVo(CompanyAdminSearchVo searchVo);

	int insert(XcompanyAdmin record);
	
}
