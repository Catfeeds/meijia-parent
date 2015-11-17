package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.partners.PartnerServiceTypeVo;

public interface PartnerServiceTypeService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    int insert(PartnerServiceType record);

    int insertSelective(PartnerServiceType record);

    PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServiceType record);

    int updateByPrimaryKey(PartnerServiceType record);
    
	List<PartnerServiceTypeVo> listChain(Short viewType);

	List<PartnerServiceType> selectByParentId(Long parentId, Short ViewType);

	PartnerServiceTypeVo ToTree(Long id, Short viewType);

	PartnerServiceType initPartnerServiceType();

	List<PartnerServiceType> selectByIds(List<Long> ids);
	



}
