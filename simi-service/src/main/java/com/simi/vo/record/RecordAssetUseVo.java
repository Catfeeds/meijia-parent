package com.simi.vo.record;

import java.util.List;

import com.simi.po.model.common.Imgs;
import com.simi.po.model.record.RecordAssetUse;
import com.simi.po.model.xcloud.XcompanyAssets;


/**
 *
 * @author :hulj
 * @Date : 2016年4月21日下午6:27:49
 * @Description: TODO
 *
 */
public class RecordAssetUseVo extends RecordAssetUse {
	
	private String fromHeadImg;
	
	private String fromName;
	
	private String fromMobile;
	
	private String toHeadImg;
	
	private List<Imgs> imgs;
	
	
	//2016年4月21日18:18:46 云平台--行政--资产管理--领用取用
	
	private String addTimeStr;		//时间
	
	// 每次 可领用多件不同种类的物品 （列表页字段展示）
	private String assetNameAndNumStr;		//领用资产名称和 数量的 字符串
	
	
	//2016年4月27日17:51:32   领取登记form页 字段（展示详情回显）
	
	private List<XcompanyAssets> companList; //资产领用 下拉  list
	
	private Long assetNum;	// 领取物品的 数量
	
	private Long useAssetId;	//已领取物品的 id
	
	
	public Long getAssetNum() {
		return assetNum;
	}

	public void setAssetNum(Long assetNum) {
		this.assetNum = assetNum;
	}


	public Long getUseAssetId() {
		return useAssetId;
	}

	public void setUseAssetId(Long useAssetId) {
		this.useAssetId = useAssetId;
	}

	public List<XcompanyAssets> getCompanList() {
		return companList;
	}

	public void setCompanList(List<XcompanyAssets> companList) {
		this.companList = companList;
	}

	public String getAssetNameAndNumStr() {
		return assetNameAndNumStr;
	}

	public void setAssetNameAndNumStr(String assetNameAndNumStr) {
		this.assetNameAndNumStr = assetNameAndNumStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

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
