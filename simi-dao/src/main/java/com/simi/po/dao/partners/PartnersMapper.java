package com.simi.po.dao.partners;


import java.util.List;
import java.util.Map;

import com.simi.po.model.partners.Partners;

public interface PartnersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Partners record);

    int insertSelective(Partners record);

    Partners selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Partners record);

    int updateByPrimaryKey(Partners record);
    
    List<Partners> selectByListPage(Map<String,Object> conditions);
}