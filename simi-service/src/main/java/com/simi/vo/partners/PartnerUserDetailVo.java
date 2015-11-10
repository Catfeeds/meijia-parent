package com.simi.vo.partners;

import java.util.List;
public class PartnerUserDetailVo extends PartnerUserVo {

	private List<PartnerServicePriceListVo> servicePrices;

	public List<PartnerServicePriceListVo> getServicePrices() {
		return servicePrices;
	}

	public void setServicePrices(List<PartnerServicePriceListVo> servicePrices) {
		this.servicePrices = servicePrices;
	}

}