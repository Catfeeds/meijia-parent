package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.Xcompany;
import com.simi.vo.xcloud.CompanySearchVo;

public interface XcompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    Long insert(Xcompany record);

    int insertSelective(Xcompany record);

    Xcompany selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(Xcompany record);

    int updateByPrimaryKey(Xcompany record);

	List<Xcompany> selectByIds(List<Long> ids);
	
	List<Xcompany> selectByListPage(CompanySearchVo searchVo);

	List<Xcompany> selectBySearchVo(CompanySearchVo searchVo);
}