package com.simi.vo.partners;

import java.util.List;

import com.simi.vo.user.UserImgVo;
public class PartnerUserDetailVo extends PartnerUserVo {
	
	private List<UserImgVo> userImgs;
	
	private List<PartnerServicePriceListVo> servicePrices;

	public List<PartnerServicePriceListVo> getServicePrices() {
		return servicePrices;
	}

	public void setServicePrices(List<PartnerServicePriceListVo> servicePrices) {
		this.servicePrices = servicePrices;
	}

	public List<UserImgVo> getUserImgs() {
		return userImgs;
	}

	public void setUserImgs(List<UserImgVo> userImgs) {
		this.userImgs = userImgs;
	}

}