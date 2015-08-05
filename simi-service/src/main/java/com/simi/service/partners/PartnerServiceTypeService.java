package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.admin.AdminAuthority;
import com.simi.po.model.partners.PartnerServiceType;
import com.simi.vo.admin.AdminAuthorityVo;
import com.simi.vo.partners.PartnerServiceTypeVo;

public interface PartnerServiceTypeService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    int insert(PartnerServiceType record);

    int insertSelective(PartnerServiceType record);

    PartnerServiceType selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServiceType record);

    int updateByPrimaryKey(PartnerServiceType record);
    
	List<PartnerServiceTypeVo> listChain();

	PartnerServiceTypeVo ToTree(Long id);
	
	PartnerServiceType initPartnerServiceType(PartnerServiceTypeVo partnerServiceTypeVo);



}
