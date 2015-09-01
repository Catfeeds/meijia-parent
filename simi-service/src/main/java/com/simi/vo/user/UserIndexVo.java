package com.simi.vo.user;

import java.math.BigDecimal;

public class UserIndexVo  {
	
	private long Id;
	
	private	String headImg;
	
	private String sex;
	
	private String provinceName;
	
	private BigDecimal restMoney;
	
	private Short userType;
	
	private String ImUserName;
	
	//与当前用户的距离.
	private String poiDistance;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public BigDecimal getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(BigDecimal restMoney) {
		this.restMoney = restMoney;
	}

	public Short getUserType() {
		return userType;
	}

	public void setUserType(Short userType) {
		this.userType = userType;
	}

	public String getImUserName() {
		return ImUserName;
	}

	public void setImUserName(String imUserName) {
		ImUserName = imUserName;
	}

	public String getPoiDistance() {
		return poiDistance;
	}

	public void setPoiDistance(String poiDistance) {
		this.poiDistance = poiDistance;
	}
}
