package com.simi.po.model.user;

import java.math.BigDecimal;
import java.util.Date;

public class Users {
 
	private Long id;

    private String mobile;
    
    private String thirdType;
    
    private String openid;
    
    private String provinceName;

    private String name;
    
    private String sex;
    
    private String introduction;
    
    private short level;
    
    private String workStart;
    
    private String workEnd;
    
    private short isDoor;
    
    private	String headImg;

    private BigDecimal restMoney;

    private Integer score;

    private Short userType;
    
    private Short isApproval;

    private Short addFrom;

    private Long addTime;

    private Long updateTime;
    
	private String realName;

	private String idCard;

	private Date birthDay;

	private Long degreeId;

	private String major;   


    public Users() {
    }


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


	public String getThirdType() {
		return thirdType;
	}


	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getProvinceName() {
		return provinceName;
	}


	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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


	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	public short getLevel() {
		return level;
	}


	public void setLevel(short level) {
		this.level = level;
	}

	public String getWorkStart() {
		return workStart;
	}


	public void setWorkStart(String workStart) {
		this.workStart = workStart;
	}


	public String getWorkEnd() {
		return workEnd;
	}


	public void setWorkEnd(String workEnd) {
		this.workEnd = workEnd;
	}


	public short getIsDoor() {
		return isDoor;
	}


	public void setIsDoor(short isDoor) {
		this.isDoor = isDoor;
	}


	public String getHeadImg() {
		return headImg;
	}


	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}


	public BigDecimal getRestMoney() {
		return restMoney;
	}


	public void setRestMoney(BigDecimal restMoney) {
		this.restMoney = restMoney;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}


	public Short getUserType() {
		return userType;
	}


	public void setUserType(Short userType) {
		this.userType = userType;
	}


	public Short getIsApproval() {
		return isApproval;
	}


	public void setIsApproval(Short isApproval) {
		this.isApproval = isApproval;
	}


	public Short getAddFrom() {
		return addFrom;
	}


	public void setAddFrom(Short addFrom) {
		this.addFrom = addFrom;
	}


	public Long getAddTime() {
		return addTime;
	}


	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}


	public Long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public Date getBirthDay() {
		return birthDay;
	}


	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
}