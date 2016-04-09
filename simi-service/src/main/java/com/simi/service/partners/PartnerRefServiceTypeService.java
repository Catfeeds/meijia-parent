package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerRefServiceType;

public interface PartnerRefServiceTypeService {
	
	int deleteByPrimaryKey(Long id);

    int insert(PartnerRefServiceType record);

    int insertSelective(PartnerRefServiceType record);

    PartnerRefServiceType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerRefServiceType record);

    int updateByPrimaryKey(PartnerRefServiceType record);
    
    PartnerRefServiceType initPartnerRefServiceType();

	int deleteByPartnerId(Long partnerId);

	List<PartnerRefServiceType> selectByServiceTypeId(Long serviceTypeId);
	
	List<PartnerRefServiceType> selectByPartnerId(Long partnerId);

}
