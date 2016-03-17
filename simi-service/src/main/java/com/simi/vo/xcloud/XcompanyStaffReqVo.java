package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanyStaffReq;


public class XcompanyStaffReqVo extends XcompanyStaffReq {

	private String headImg;
	
	private String name;
	
	private String companyName;
	
	private Short reqType;

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Short getReqType() {
		return reqType;
	}

	public void setReqType(Short reqType) {
		this.reqType = reqType;
	}
}
