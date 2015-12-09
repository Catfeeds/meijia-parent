package com.simi.vo.partners;

import com.simi.po.model.partners.Partners;

public class PartnersVo extends Partners{
	
	//private Date registerTime;

	private String spiderUrl;

	

	/*public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}*/

	@Override
	public String getSpiderUrl() {
		return spiderUrl;
	}

	@Override
	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}
	
	
}
