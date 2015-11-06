package com.simi.po.dao.partners;

import com.simi.po.model.partners.PartnerServicePriceDetail;

public interface PartnerServicePriceDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerServicePriceDetail record);

    int insertSelective(PartnerServicePriceDetail record);

    PartnerServicePriceDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerServicePriceDetail record);

    int updateByPrimaryKey(PartnerServicePriceDetail record);
}