package com.simi.vo.partners;


import com.simi.po.model.partners.PartnerServicePriceDetail;


public class PartnerServicePriceDetailVo extends PartnerServicePriceDetail {
	
	private String name;
	
	private Long parentId;
		
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
