package com.simi.vo.partners;

import java.util.List;

import com.simi.po.model.partners.PartnerUsers;
import com.simi.po.model.user.Tags;

public class PartnerUserVo extends PartnerUsers {
	

	private String companyName;

	private String serviceTypeName;

	private String name;
	
	private String mobile;

	private String headImg;

	private String cityAndRegion;
	
	private String responseTimeName;

	private String introduction;

	private List<Tags> userTags;
	
	private Long addTime;

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

	public String getCityAndRegion() {
		return cityAndRegion;
	}

	public void setCityAndRegion(String cityAndRegion) {
		this.cityAndRegion = cityAndRegion;
	}


	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<Tags> getUserTags() {
		return userTags;
	}

	public void setUserTags(List<Tags> userTags) {
		this.userTags = userTags;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getResponseTimeName() {
		return responseTimeName;
	}

	public void setResponseTimeName(String responseTimeName) {
		this.responseTimeName = responseTimeName;
	}

	@Override
	public Long getAddTime() {
		return addTime;
	}

	@Override
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

}