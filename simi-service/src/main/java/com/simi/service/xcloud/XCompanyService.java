package com.simi.service.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.Xcompany;
import com.simi.vo.AppResultData;
import com.simi.vo.xcloud.CompanySearchVo;
import com.simi.vo.xcloud.XcompanyVo;

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

	List<XcompanyVo> getVos(List<Xcompany> list);

	AppResultData<Object> regExtend(Long userId, Long companyId);

}
