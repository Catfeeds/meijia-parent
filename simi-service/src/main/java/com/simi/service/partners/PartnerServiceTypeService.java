package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.partners.PartnerServicePriceDetailVo;
import com.simi.vo.partners.PartnerServicePriceDetailVoAll;
import com.simi.vo.partners.PartnerServiceTypeSearchVo;
import com.simi.vo.partners.PartnerServiceTypeVo;

public interface PartnerServiceTypeService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    int insert(PartnerServiceType record);

    int insertSelective(PartnerServiceType record);

    PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServiceType record);

    int updateByPrimaryKey(PartnerServiceType record);
    
	PartnerServiceType initPartnerServiceType();

	List<PartnerServiceType> selectByIds(List<Long> ids);

	List<PartnerServiceTypeVo> listChain(Short viewType, List<Long> partnerIds);

	PartnerServiceTypeVo ToTree(Long id, Short viewType, List<Long> partnerIds);

	List<PartnerServiceType> selectBySearchVo(PartnerServiceTypeSearchVo searchVo);

	List<PartnerServiceType> selectByParentId(Long parentId);

	List<PartnerServiceType> selectByPartnerIdIn(Long partnerId);

	PartnerServicePriceDetailVoAll getPartnerPriceList(PartnerServiceType item);
	
}
