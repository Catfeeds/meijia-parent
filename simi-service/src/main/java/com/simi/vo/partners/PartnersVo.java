package com.simi.vo.partners;

import com.simi.po.model.partners.Partners;

public class PartnersVo extends Partners{
	
	private String registerTime;

	private String spiderUrl;

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getSpiderUrl() {
		return spiderUrl;
	}

	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}
	
	
}
