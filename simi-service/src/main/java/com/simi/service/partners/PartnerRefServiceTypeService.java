package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerRefServiceType;
import com.simi.vo.partners.PartnersSearchVo;

public interface PartnerRefServiceTypeService {

	int deleteByPrimaryKey(Long id);

	int insert(PartnerRefServiceType record);

	int insertSelective(PartnerRefServiceType record);

	int updateByPrimaryKeySelective(PartnerRefServiceType record);

	int updateByPrimaryKey(PartnerRefServiceType record);

	PartnerRefServiceType initPartnerRefServiceType();

	int deleteByPartnerId(Long partnerId);

	PartnerRefServiceType selectByPrimaryKey(Long id);

	List<PartnerRefServiceType> selectBySearchVo(PartnersSearchVo searchVo);

}
