package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanyAssets;

/**
 *
 * @author :hulj
 * @Date : 2016年4月22日下午6:58:25
 * @Description: 
 *		
 *		库存管理 列表页 VO
 */
public class XcompanyAssetVo extends XcompanyAssets {
	
	private Long assetNum;
	
	private String addTimeStr;
	
	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Long getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(Long assetNum) {
		this.assetNum = assetNum;
	}
	
	
}
