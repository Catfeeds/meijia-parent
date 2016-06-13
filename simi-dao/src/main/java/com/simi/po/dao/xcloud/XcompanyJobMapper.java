package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyJob;
import com.simi.vo.xcloud.CompanyJobSearchVo;

public interface XcompanyJobMapper {
    int deleteByPrimaryKey(Long jobId);

    int insert(XcompanyJob record);

    int insertSelective(XcompanyJob record);

    XcompanyJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(XcompanyJob record);

    int updateByPrimaryKey(XcompanyJob record);
     
    List<XcompanyJob> selectByListPage(CompanyJobSearchVo searchVo);
    
    List<XcompanyJob> selectBySearchVo(CompanyJobSearchVo searchVo);
}