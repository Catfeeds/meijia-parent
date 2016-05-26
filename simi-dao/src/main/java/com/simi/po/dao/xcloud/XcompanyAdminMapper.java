package com.simi.po.dao.xcloud;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyAdmin;
import com.simi.vo.xcloud.CompanyAdminSearchVo;

public interface XcompanyAdminMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcompanyAdmin record);

    int insertSelective(XcompanyAdmin record);

    XcompanyAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcompanyAdmin record);

    int updateByPrimaryKey(XcompanyAdmin record);
    
    List<XcompanyAdmin> selectByListPage(CompanyAdminSearchVo searchVo);
    
    List<XcompanyAdmin> selectBySearchVo(CompanyAdminSearchVo searchVo);
}