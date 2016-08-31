package com.simi.vo.partners;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.simi.base.model.models.ChainEntity;


public class PartnerServiceTypeTreeVo extends ChainEntity<Integer, PartnerServiceTypeTreeVo> implements Serializable{

	private String isEnableName;
	private Long parentId;
	private List<PartnerServiceTypeTreeVo> childList = new ArrayList<PartnerServiceTypeTreeVo>();
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<PartnerServiceTypeTreeVo> getChildList() {
		return childList;
	}
	public void setChildList(List<PartnerServiceTypeTreeVo> childList) {
		this.childList = childList;
	}
	public String getIsEnableName() {
		return isEnableName;
	}
	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
	}
	
	



}
