package com.simi.vo.user;



public class UserImVo extends UserViewVo {
	
	private String lastIm;
	
	private String lastImTimeStr;
	
	private Long lastImTime;

	public String getLastIm() {
		return lastIm;
	}

	public void setLastIm(String lastIm) {
		this.lastIm = lastIm;
	}

	public Long getLastImTime() {
		return lastImTime;
	}

	public void setLastImTime(Long lastImTime) {
		this.lastImTime = lastImTime;
	}

	public String getLastImTimeStr() {
		return lastImTimeStr;
	}

	public void setLastImTimeStr(String lastImTimeStr) {
		this.lastImTimeStr = lastImTimeStr;
	}

}
