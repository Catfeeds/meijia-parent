package com.simi.vo.partners;

import com.simi.po.model.partners.Partners;

public class PartnersVo extends Partners{
	
	private String create;
	
	private String userName;
	
	private String mobile;
	
	private int totalUser;
	
	//private Date registerTime;

	private String spiderUrl;

	

	/*public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}*/

	@Override
	public String getSpiderUrl() {
		return spiderUrl;
	}

	@Override
	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public int getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
