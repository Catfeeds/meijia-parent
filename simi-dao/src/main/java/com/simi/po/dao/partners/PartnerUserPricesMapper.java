package com.simi.po.dao.partners;

import com.simi.po.model.partners.PartnerUserPrices;

public interface PartnerUserPricesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerUserPrices record);

    int insertSelective(PartnerUserPrices record);

    PartnerUserPrices selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerUserPrices record);

    int updateByPrimaryKey(PartnerUserPrices record);
}