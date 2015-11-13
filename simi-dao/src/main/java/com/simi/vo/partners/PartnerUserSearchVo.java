package com.simi.vo.partners;

import java.util.List;

public class PartnerUserSearchVo {
	
	 private Long partnerId;

	 private Long serviceTypeId;
	 
	 private List<Long> serviceTypeIds;
	 
	 private String name;
	 
	 private String mobile;


	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<Long> getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(List<Long> serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}	 
}
