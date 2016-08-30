package com.simi.vo.partners;

import java.util.List;

public class PartnerUserServiceTypeVo {
	
	 private Long partnerId;
	 
	 private Long userId;
	 
	 private Long serviceTypeId;
	 
	 private List<Long> serviceTypeIds;
	 	 
	 private Long parentId;
	 
	 private Long extendId;
	 
	 private Short isEnable;
	 
	 private String keyword;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	 
}
