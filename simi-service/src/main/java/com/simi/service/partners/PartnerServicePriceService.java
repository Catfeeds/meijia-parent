package com.simi.service.partners;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.simi.po.model.partners.PartnerServicePrice;
import com.simi.vo.partners.PartnerServicePriceSearchVo;
import com.simi.vo.partners.PartnerServicePriceTreeVo;
import com.simi.vo.partners.PartnerServiceTypeTreeVo;

public interface PartnerServicePriceService {

	int deleteByPrimaryKey(Long servicePriceId);

	Long insert(PartnerServicePrice record);

	Long insertSelective(PartnerServicePrice record);

	PartnerServicePrice selectByPrimaryKey(Long servicePriceId);

	int updateByPrimaryKeySelective(PartnerServicePrice record);

	int updateByPrimaryKey(PartnerServicePrice record);

	PartnerServicePrice initPartnerServicePrice();

	List<PartnerServicePrice> selectBySearchVo(PartnerServicePriceSearchVo searchVo);

	List<PartnerServiceTypeTreeVo> listChain(List<Long> partnerIds);

	PageInfo selectByListPage(PartnerServicePriceSearchVo searchVo, int pageNo, int pageSize);

}
