package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.Xcompany;

public class XcompanyOaVo extends Xcompany {

	private Integer totalStaff;
	
	private String createName;

	public Integer getTotalStaff() {
		return totalStaff;
	}

	public void setTotalStaff(Integer totalStaff) {
		this.totalStaff = totalStaff;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
}
