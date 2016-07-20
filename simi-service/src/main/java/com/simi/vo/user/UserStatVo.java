package com.simi.vo.user;

import com.simi.po.model.user.Users;

public class UserStatVo extends Users {
	
	public int totalCards;
	
	public int totalFeeds;
	
	public int totalOrders;
	
	public int totalCompanys;
	
	public Long groupId;
	
	public String groupName;

	public int getTotalCards() {
		return totalCards;
	}

	public void setTotalCards(int totalCards) {
		this.totalCards = totalCards;
	}

	public int getTotalFeeds() {
		return totalFeeds;
	}

	public void setTotalFeeds(int totalFeeds) {
		this.totalFeeds = totalFeeds;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	public int getTotalCompanys() {
		return totalCompanys;
	}

	public void setTotalCompanys(int totalCompanys) {
		this.totalCompanys = totalCompanys;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
}
