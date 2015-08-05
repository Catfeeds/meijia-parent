package com.simi.po.dao.partners;

import com.simi.po.model.partners.PartnerRefServiceType;

public interface PartnerRefServiceTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerRefServiceType record);

    int insertSelective(PartnerRefServiceType record);

    PartnerRefServiceType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerRefServiceType record);

    int updateByPrimaryKey(PartnerRefServiceType record);
}