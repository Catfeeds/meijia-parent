package com.simi.service.partners;

import com.simi.po.model.partners.PartnerServicePriceDetail;

public interface PartnerServicePriceDetailService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    Long insert(PartnerServicePriceDetail record);

    Long insertSelective(PartnerServicePriceDetail record);

    PartnerServicePriceDetail selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServicePriceDetail record);

    int updateByPrimaryKey(PartnerServicePriceDetail record);
    	
	PartnerServicePriceDetail initPartnerServicePriceDetail();
	



}
