package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerRefServiceType;

public interface PartnerRefServiceTypeMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByPartnerId(Long partnerId);

    int insert(PartnerRefServiceType record);

    int insertSelective(PartnerRefServiceType record);

    PartnerRefServiceType selectByPrimaryKey(Long id);

    List<PartnerRefServiceType> selectByPartnerId(Long partnerId);

    int updateByPrimaryKeySelective(PartnerRefServiceType record);

    int updateByPrimaryKey(PartnerRefServiceType record);
    
    List<PartnerRefServiceType> selectServiceTypeByPartnerIdAndParentId(Long partnerId,Long parentId);
   
    List<PartnerRefServiceType> selectSubServiceTypeByPartnerIdAndParentId(Long partnerId,Long parentId);

	List<PartnerRefServiceType> selectByServiceTypeId(Long serviceTypeId);
    
}