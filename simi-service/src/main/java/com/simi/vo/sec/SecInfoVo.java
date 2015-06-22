package com.simi.vo.sec;

import java.util.Date;


public class SecInfoVo {
	
private Long id;
private String mobile;
private String name;
private String sex;
private String headImg;
private Date birthDay;
private Long cityId;
private String cityName;
private Long addTime;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getMobile() {
	return mobile;
}

public void setMobile(String mobile) {
	this.mobile = mobile;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getSex() {
	return sex;
}

public void setSex(String sex) {
	this.sex = sex;
}

public String getHeadImg() {
	return headImg;
}

public void setHeadImg(String headImg) {
	this.headImg = headImg;
}

public Date getBirthDay() {
	return birthDay;
}

public void setBirthDay(Date birthDay) {
	this.birthDay = birthDay;
}

public Long getCityId() {
	return cityId;
}

public void setCityId(Long cityId) {
	this.cityId = cityId;
}

public Long getAddTime() {
	return addTime;
}

public void setAddTime(Long addTime) {
	this.addTime = addTime;
}

public String getCityName() {
	return cityName;
}

public void setCityName(String cityName) {
	this.cityName = cityName;
}

}
