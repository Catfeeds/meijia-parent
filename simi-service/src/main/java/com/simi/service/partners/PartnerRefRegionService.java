package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerRefRegion;

public interface PartnerRefRegionService {
	
	int insert(PartnerRefRegion record);

    int insertSelective(PartnerRefRegion record);
    
    List<PartnerRefRegion> selectByPartnerId(Long partnerId);
    
    int deleteByPartnerId(Long partnerId);
    
    PartnerRefRegion initPartnerRefRegion();

}
