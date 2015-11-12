package com.simi.vo.partners;


import com.simi.po.model.partners.PartnerServicePriceDetail;


public class PartnerServicePriceDetailVo extends PartnerServicePriceDetail {
	
	private String name;
	
	private Long parentId;
	
	private Integer no;
		
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}


}
