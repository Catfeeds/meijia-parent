package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerRefCity;

public interface PartnerRefCityService {
	
	int deleteByPrimaryKey(Long id);

    int deleteByPartnerId(Long partnerId);
	
	int insert(PartnerRefCity record);

    int insertSelective(PartnerRefCity record);

    PartnerRefCity selectByPrimaryKey(Long id);

    List<PartnerRefCity> selectByPartnerId(Long partnerId);

    int updateByPrimaryKeySelective(PartnerRefCity record);

    int updateByPrimaryKey(PartnerRefCity record);
    
    PartnerRefCity initPartnerRefCity();
    

}
