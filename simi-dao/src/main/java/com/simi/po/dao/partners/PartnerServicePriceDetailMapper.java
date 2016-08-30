package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePriceDetail;
import com.simi.vo.partners.PartnerUserServiceTypeVo;

public interface PartnerServicePriceDetailMapper {
	int deleteByPrimaryKey(Long id);

	int deleteByServiceTypeId(Long servicePriceId);

	Long insert(PartnerServicePriceDetail record);

	Long insertSelective(PartnerServicePriceDetail record);

	PartnerServicePriceDetail selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PartnerServicePriceDetail record);

	int updateByPrimaryKey(PartnerServicePriceDetail record);

	PartnerServicePriceDetail selectByServicePriceId(Long servicePriceId);

	List<PartnerServicePriceDetail> selectByServicePriceIds(List<Long> servicePriceIds);

	List<PartnerServicePriceDetail> selectByUserId(Long userId);

	List<PartnerServicePriceDetail> selectByListPage(PartnerUserServiceTypeVo searchVo);

	List<PartnerServicePriceDetail> selectBySearchVo(PartnerUserServiceTypeVo searchVo);
}