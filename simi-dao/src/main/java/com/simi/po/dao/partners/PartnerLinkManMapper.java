package com.simi.po.dao.partners;

import com.simi.po.model.partners.PartnerLinkMan;

public interface PartnerLinkManMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerLinkMan record);

    int insertSelective(PartnerLinkMan record);

    PartnerLinkMan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerLinkMan record);

    int updateByPrimaryKey(PartnerLinkMan record);
}