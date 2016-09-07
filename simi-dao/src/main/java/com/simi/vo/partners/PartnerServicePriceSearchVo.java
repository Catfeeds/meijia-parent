package com.simi.vo.partners;

import java.util.List;

public class PartnerServicePriceSearchVo {
	
	 private Long servicePriceId;
	 
	 private List<Long> servicePriceIds;

	 private Long partnerId;
	 
	 private List<Long> partnerIds;
	 
	 private Long serviceTypeId;
	 
	 private List<Long> serviceTypeIds;

	 private Long userId;
	 
	 private List<Long> userIds;
	 
	 private Long extendId;
	 
	 private Short isEnable;
	 
	 private String keyword;
	 
	 private String tags;

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public List<Long> getServiceTypeIds() {
		return serviceTypeIds;
	}

	public void setServiceTypeIds(List<Long> serviceTypeIds) {
		this.serviceTypeIds = serviceTypeIds;
	}

	public Long getExtendId() {
		return extendId;
	}

	public void setExtendId(Long extendId) {
		this.extendId = extendId;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public List<Long> getPartnerIds() {
		return partnerIds;
	}

	public void setPartnerIds(List<Long> partnerIds) {
		this.partnerIds = partnerIds;
	}

	public List<Long> getServicePriceIds() {
		return servicePriceIds;
	}

	public void setServicePriceIds(List<Long> servicePriceIds) {
		this.servicePriceIds = servicePriceIds;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	 
}
