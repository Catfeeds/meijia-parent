package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.vo.partners.PartnerUserServiceTypeVo;

public interface PartnerServicePriceDetailService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    Long insert(PartnerServicePriceDetail record);

    Long insertSelective(PartnerServicePriceDetail record);

    PartnerServicePriceDetail selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServicePriceDetail record);

    int updateByPrimaryKey(PartnerServicePriceDetail record);
    	
	PartnerServicePriceDetail initPartnerServicePriceDetail();

	PartnerServicePriceDetail selectByServicePriceId(Long servicePriceId);

	int deleteByServiceTypeId(Long servicePriceId);

	List<PartnerServicePriceDetail> selectByServicePriceIds(List<Long> servicePriceIds);

	List<PartnerServicePriceDetail> selectByUserId(Long userId);

	List<PartnerServicePriceDetail> selectByListPage(
			PartnerUserServiceTypeVo searchVo);

	List<PartnerServicePriceDetail> selectBySearchVo(PartnerUserServiceTypeVo searchVo);
	



}
