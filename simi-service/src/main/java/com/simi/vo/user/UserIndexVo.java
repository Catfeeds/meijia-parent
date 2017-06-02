package com.simi.vo.user;

import java.math.BigDecimal;

public class UserIndexVo  {
	
	private long Id;
	
	private String mobile;
	
	private	String headImg;
	
	private String name;
	
	private String sex;
	
	private String provinceName;
	
	private BigDecimal restMoney;
	
	private Short userType;
	
	private String ImUserName;
	
	//与当前用户的距离.
	private String poiDistance;
			
	private int totalFriends;
	
	private Integer score;
	
	private Short isFriend;
		
	private Integer exp;
	
	private String level;
	
	private Integer levelMin;
	
	private Integer levelMax;
	
	private String levelBanner;
	
    private Long companyId;
    
    private String companyName;
    
    private String jobName;
    
    private String deptName;

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

	public int getTotalFriends() {
		return totalFriends;
	}

	public void setTotalFriends(int totalFriends) {
		this.totalFriends = totalFriends;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Short getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(Short isFriend) {
		this.isFriend = isFriend;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getLevelMin() {
		return levelMin;
	}

	public void setLevelMin(Integer levelMin) {
		this.levelMin = levelMin;
	}

	public Integer getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(Integer levelMax) {
		this.levelMax = levelMax;
	}

	public String getLevelBanner() {
		return levelBanner;
	}

	public void setLevelBanner(String levelBanner) {
		this.levelBanner = levelBanner;
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

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
