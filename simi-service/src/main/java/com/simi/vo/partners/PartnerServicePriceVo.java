package com.simi.vo.partners;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.simi.base.model.models.ChainEntity;


public class PartnerServicePriceVo extends ChainEntity<Integer, PartnerServicePriceVo> implements Serializable{

	private Integer id;
	
	private Long parentId;
	
	private List<PartnerServicePriceVo> childList = new ArrayList<PartnerServicePriceVo>();
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<PartnerServicePriceVo> getChildList() {
		return childList;
	}
	public void setChildList(List<PartnerServicePriceVo> childList) {
		this.childList = childList;
	}
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}


	



}
