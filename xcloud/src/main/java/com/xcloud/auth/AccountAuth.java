package com.xcloud.auth;

import java.util.List;

import com.simi.po.model.xcloud.XcompanyAdmin;

public class AccountAuth {

	private Long userId;
	
	private String name;
	
	private String realName;
	
	private String headImg;
	
	private String mobile;
	
	private Long companyId;
	
	private String companyName;
	
	private String shortName;
	
	private Long staffId;
	
	private List<XcompanyAdmin> companyList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<XcompanyAdmin> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<XcompanyAdmin> companyList) {
		this.companyList = companyList;
	}



}
