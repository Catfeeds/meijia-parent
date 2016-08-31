package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.vo.partners.PartnerServicePriceSearchVo;

public interface PartnerServicePriceMapper {
	int deleteByPrimaryKey(Long servicePriceId);

	Long insert(PartnerServicePrice record);

	Long insertSelective(PartnerServicePrice record);

	PartnerServicePrice selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(PartnerServicePrice record);

	int updateByPrimaryKey(PartnerServicePrice record);

	List<PartnerServicePrice> selectByListPage(PartnerServicePriceSearchVo searchVo);

	List<PartnerServicePrice> selectBySearchVo(PartnerServicePriceSearchVo searchVo);
}