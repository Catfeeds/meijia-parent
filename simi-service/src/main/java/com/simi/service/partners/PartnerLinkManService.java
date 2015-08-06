package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerLinkMan;

public interface PartnerLinkManService {
	
	int deleteByPrimaryKey(Long id);

	int deleteByPartnerId(Long partnerId);

    int insert(PartnerLinkMan record);

    int insertSelective(PartnerLinkMan record);

    PartnerLinkMan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerLinkMan record);

    int updateByPrimaryKey(PartnerLinkMan record);

    PartnerLinkMan initPartnerLinkMan();
    
    List<PartnerLinkMan> selectByPartnerId(Long partnerId);
}
