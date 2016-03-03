package com.simi.vo.partners;


import com.simi.po.model.partners.PartnerServicePriceDetail;


public class PartnerServicePriceDetailVo extends PartnerServicePriceDetail {
	
	
	private  Long serviceTypeId;
	
	private String name;
	
	private Long parentId;
	
	private Integer no;
	
	private Short isEnable;
	
	private Long partnerId;
		
	public Long getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
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
	public Short getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

}
