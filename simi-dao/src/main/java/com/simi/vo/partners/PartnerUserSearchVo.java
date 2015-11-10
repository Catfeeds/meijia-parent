package com.simi.vo.partners;

public class PartnerUserSearchVo {
	
	 private Long partnerId;

	 private Long serviceTypeId;
	 
	 private String name;
	 
	 private String mobile;
	 
	 private Short isApproval;

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

	public Short getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Short isApproval) {
		this.isApproval = isApproval;
	}
	 


	 
	 
}
