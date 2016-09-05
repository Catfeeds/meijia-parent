package com.simi.vo.dict;

import com.simi.po.model.dict.DictAd;

public class DictAdVo extends DictAd{

	private String adTypeName;
	
	private int totalHit;	//广告 点击次数
	
	public int getTotalHit() {
		return totalHit;
	}

	public void setTotalHit(int totalHit) {
		this.totalHit = totalHit;
	}

	public String getAdTypeName() {
		return adTypeName;
	}

	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}

}
