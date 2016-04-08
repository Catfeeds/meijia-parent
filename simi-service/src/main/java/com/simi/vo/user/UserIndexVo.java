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
	
	private int	totalCard;
	
	private int totalCoupon;
	
	private int totalFriends;
	
	private Integer score;
	
	private Short isFriend;
	
	private int totalFeed;
	
	private Integer exp;
	

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

	public int getTotalCard() {
		return totalCard;
	}

	public void setTotalCard(int totalCard) {
		this.totalCard = totalCard;
	}

	public int getTotalCoupon() {
		return totalCoupon;
	}

	public void setTotalCoupon(int totalCoupon) {
		this.totalCoupon = totalCoupon;
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

	public int getTotalFeed() {
		return totalFeed;
	}

	public void setTotalFeed(int totalFeed) {
		this.totalFeed = totalFeed;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}
}
