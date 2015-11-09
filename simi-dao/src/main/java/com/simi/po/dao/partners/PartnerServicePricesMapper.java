package com.simi.po.dao.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePrices;
import com.simi.po.model.partners.PartnerServiceType;

public interface PartnerServicePricesMapper {
    int deleteByPrimaryKey(Long servicePriceId);

    Long insert(PartnerServicePrices record);

    Long insertSelective(PartnerServicePrices record);

    PartnerServicePrices selectByPrimaryKey(Long servicePriceId);

    int updateByPrimaryKeySelective(PartnerServicePrices record);

    int updateByPrimaryKey(PartnerServicePrices record);
    
    List<PartnerServicePrices> selectByParentId(Long  partnerId);
}