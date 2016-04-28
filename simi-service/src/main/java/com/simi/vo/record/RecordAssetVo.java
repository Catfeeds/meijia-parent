package com.simi.vo.record;

import java.util.List;

import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssets;
import com.simi.po.model.xcloud.XcompanyAssets;
import com.simi.po.model.xcloud.XcompanySetting;


/**
 *
 * @author :hulj
 * @Date : 2016年4月22日上午10:16:27
 * @Description: 
 *		
 *     公司资产Vo-- 用于 云平台
 *			
 */
public class RecordAssetVo extends RecordAssets {
	
	private List<Imgs> imgs;
	
	private String addTimeStr;
	// 资产选择 ，下拉VO
	private List<XcompanySetting> xCompSettingList;
	
	
	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public List<XcompanySetting> getxCompSettingList() {
		return xCompSettingList;
	}

	public void setxCompSettingList(List<XcompanySetting> xCompSettingList) {
		this.xCompSettingList = xCompSettingList;
	}

	public List<Imgs> getImgs() {
		return imgs;
	}

	public void setImgs(List<Imgs> imgs) {
		this.imgs = imgs;
	}

	
}
