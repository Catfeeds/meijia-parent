package com.simi.vo.partners;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.simi.base.model.models.ChainEntity;


public class PartnerServicePriceTreeVo extends ChainEntity<Integer, PartnerServicePriceTreeVo> implements Serializable{

	private String isEnableName;
	private Long parentId;
	private List<PartnerServicePriceTreeVo> childList = new ArrayList<PartnerServicePriceTreeVo>();
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<PartnerServicePriceTreeVo> getChildList() {
		return childList;
	}
	public void setChildList(List<PartnerServicePriceTreeVo> childList) {
		this.childList = childList;
	}
	
	public String getIsEnableName() {
		return isEnableName;
	}
	public void setIsEnableName(String isEnableName) {
		this.isEnableName = isEnableName;
	}
}
