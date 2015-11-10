package com.simi.vo.partners;

import java.util.List;

import com.simi.po.model.user.Tags;
import com.simi.vo.user.UserImgVo;
public class PartnerUserVo {
	
	private Long userId;
	
	private String name;
	
	private String headImg;
	
	private String cityAndRegion;
	
	private String responseTime;
	
	private String serviceTypeName;
	
	private String introduction;
	
	private List<Tags> userTags;
	
	private List<UserImgVo> userImgs;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getCityAndRegion() {
		return cityAndRegion;
	}

	public void setCityAndRegion(String cityAndRegion) {
		this.cityAndRegion = cityAndRegion;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
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

	public List<UserImgVo> getUserImgs() {
		return userImgs;
	}

	public void setUserImgs(List<UserImgVo> userImgs) {
		this.userImgs = userImgs;
	}
	
	
	

}