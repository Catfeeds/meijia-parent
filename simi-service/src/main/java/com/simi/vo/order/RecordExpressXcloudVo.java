package com.simi.vo.order;

import com.simi.po.model.record.RecordExpress;


public class RecordExpressXcloudVo extends RecordExpress{
	
	//快递服务商名称
    private String expressName;
    
    private String expressTypeName;
    
    private String isDoneName;

    private String isCloseName;
    
    private String addTimeStr;

    private String updateTimeStr;

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressTypeName() {
		return expressTypeName;
	}

	public void setExpressTypeName(String expressTypeName) {
		this.expressTypeName = expressTypeName;
	}

	public String getIsDoneName() {
		return isDoneName;
	}

	public void setIsDoneName(String isDoneName) {
		this.isDoneName = isDoneName;
	}

	public String getIsCloseName() {
		return isCloseName;
	}

	public void setIsCloseName(String isCloseName) {
		this.isCloseName = isCloseName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
    
    
    
}
