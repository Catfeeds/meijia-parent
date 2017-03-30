package com.simi.vo.partners;

import java.util.List;

public class PartnersSearchVo {
	
	private Long userId;
	
	private Long partnerId;
	
	private Long serviceTypeId;
	
	private Long parentId;
	
	private Long spiderPartnerId;
	
	private List<Long> spiderPartnerIds;
	
	private String companyName;

	private String shortName;

	private Short status;

	private Short isCooperate;

	private String serviceType;

	private Short companySize;

	public Short getCompanySize() {
		return companySize;
	}

	public void setCompanySize(Short companySize) {
		this.companySize = companySize;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Short getIsCooperate() {
		return isCooperate;
	}

	public void setIsCooperate(Short isCooperate) {
		this.isCooperate = isCooperate;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Long getSpiderPartnerId() {
		return spiderPartnerId;
	}

	public void setSpiderPartnerId(Long spiderPartnerId) {
		this.spiderPartnerId = spiderPartnerId;
	}

	public List<Long> getSpiderPartnerIds() {
		return spiderPartnerIds;
	}

	public void setSpiderPartnerIds(List<Long> spiderPartnerIds) {
		this.spiderPartnerIds = spiderPartnerIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
