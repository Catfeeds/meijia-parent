package com.simi.vo.po;

import com.simi.po.model.op.AppTools;

public class AppToolsVo extends AppTools {

	//添加时间戳
	private String addTimeStr ;
	
	//应用状态
	private Short status;

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
