package com.simi.vo.op;

import com.simi.po.model.xcloud.XcompanySetting;

public class OpExtVo extends XcompanySetting {

	private String expIn;
	
	private String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getExpIn() {
		return expIn;
	}

	public void setExpIn(String expIn) {
		this.expIn = expIn;
	}

}
