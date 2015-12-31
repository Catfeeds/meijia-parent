package com.simi.vo.partners;


import com.simi.po.model.partners.PartnerServicePriceDetail;


public class PartnerServicePriceDetailVoAll extends PartnerServicePriceDetail {
	
	private String name;
	
	private Long parentId;
	
	private Long partnerId;
	
	private Integer no;
	
	private String isEnableName;
		
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
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public String getIsEnableName() {
		return isEnableName;
	}
	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
	}


}
