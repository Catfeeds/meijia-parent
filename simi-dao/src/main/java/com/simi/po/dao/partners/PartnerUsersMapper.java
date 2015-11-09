package com.simi.po.dao.partners;

import com.simi.po.model.partners.PartnerUsers;

public interface PartnerUsersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerUsers record);

    int insertSelective(PartnerUsers record);

    PartnerUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerUsers record);

    int updateByPrimaryKey(PartnerUsers record);
}