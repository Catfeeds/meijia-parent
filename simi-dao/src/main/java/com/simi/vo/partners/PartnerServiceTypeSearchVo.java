package com.simi.vo.partners;

import java.util.List;

public class PartnerServiceTypeSearchVo {
	
	 private List<Long> partnerIds;
	 
	 private Long serviceTypeId;
	 
	 private List<Long> serviceTypeIds;
	 
	 private String name;
	 	 
	 private Long parentId;
	 
	 private Short viewType;
	 
	 private Short isEnable;
	 
	

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(List<Long> serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Long> getPartnerIds() {
		return partnerIds;
	}

	public void setPartnerIds(List<Long> partnerIds) {
		this.partnerIds = partnerIds;
	}

	public Short getViewType() {
		return viewType;
	}

	public void setViewType(Short viewType) {
		this.viewType = viewType;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}


	
}
