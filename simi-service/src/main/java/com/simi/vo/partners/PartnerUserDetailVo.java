package com.simi.vo.partners;

import java.util.List;

import com.simi.po.model.user.UserImgs;
public class PartnerUserDetailVo extends PartnerUserVo {
	
	private List<UserImgs> userImgs;
	
	private List<PartnerServicePriceListVo> servicePrices;

	public List<PartnerServicePriceListVo> getServicePrices() {
		return servicePrices;
	}

	public void setServicePrices(List<PartnerServicePriceListVo> servicePrices) {
		this.servicePrices = servicePrices;
	}

	public List<UserImgs> getUserImgs() {
		return userImgs;
	}

	public void setUserImgs(List<UserImgs> userImgs) {
		this.userImgs = userImgs;
	}

}