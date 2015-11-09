package com.simi.service.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerServicePrices;
import com.simi.vo.partners.PartnerServicePriceVo;

public interface PartnerServicePriceService {
	
	int deleteByPrimaryKey(Long serviceTypeId);

    Long insert(PartnerServicePrices record);

    Long insertSelective(PartnerServicePrices record);

    PartnerServicePrices selectByPrimaryKey(Long serviceTypeId);

    int updateByPrimaryKeySelective(PartnerServicePrices record);

    int updateByPrimaryKey(PartnerServicePrices record);
    
	List<PartnerServicePriceVo> listChain();

	PartnerServicePriceVo ToTree(Long id);
	
	PartnerServicePrices initPartnerServicePrices();
	



}
