package com.simi.vo.dict;

import com.simi.po.model.dict.DictAd;

public class DictAdVo extends DictAd{

	private String adTypeName;
	
	private Long totalHit;	//广告 点击次数
	
	public Long getTotalHit() {
		return totalHit;
	}

	public void setTotalHit(Long totalHit) {
		this.totalHit = totalHit;
	}

	public String getAdTypeName() {
		return adTypeName;
	}

	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}

}
