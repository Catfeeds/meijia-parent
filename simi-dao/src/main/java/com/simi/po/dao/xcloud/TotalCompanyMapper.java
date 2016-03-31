package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.TotalCompany;
import com.simi.vo.xcloud.CompanySearchVo;

public interface TotalCompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TotalCompany record);

    int insertSelective(TotalCompany record);

    TotalCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TotalCompany record);

    int updateByPrimaryKey(TotalCompany record);
    
    List<TotalCompany> selectBySearchVo(CompanySearchVo searchVo);
}