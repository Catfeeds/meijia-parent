package com.simi.vo.partners;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.simi.base.model.models.ChainEntity;


public class PartnerServiceTypeVo extends ChainEntity<Integer, PartnerServiceTypeVo> implements Serializable{


	private Long parentId;
	private List<PartnerServiceTypeVo> childList = new ArrayList<PartnerServiceTypeVo>();
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<PartnerServiceTypeVo> getChildList() {
		return childList;
	}
	public void setChildList(List<PartnerServiceTypeVo> childList) {
		this.childList = childList;
	}


	



}
