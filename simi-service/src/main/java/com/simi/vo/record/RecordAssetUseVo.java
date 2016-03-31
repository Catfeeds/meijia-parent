package com.simi.vo.record;

import java.util.List;

import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssetUse;


public class RecordAssetUseVo extends RecordAssetUse {
	
	private String fromHeadImg;
	
	private String fromName;
	
	private String fromMobile;
	
	private String toHeadImg;
	
	private List<Imgs> imgs;

	public List<Imgs> getImgs() {
		return imgs;
	}

	public void setImgs(List<Imgs> imgs) {
		this.imgs = imgs;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(String fromMobile) {
		this.fromMobile = fromMobile;
	}

	public String getFromHeadImg() {
		return fromHeadImg;
	}

	public void setFromHeadImg(String fromHeadImg) {
		this.fromHeadImg = fromHeadImg;
	}

	public String getToHeadImg() {
		return toHeadImg;
	}

	public void setToHeadImg(String toHeadImg) {
		this.toHeadImg = toHeadImg;
	}

	
}
