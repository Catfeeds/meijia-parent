package com.simi.vo.user;

import com.simi.po.model.user.UserDetailPay;

public class UserDetailPayVo extends UserDetailPay{

	private String orderTypeName;

    private String addTimeStr;

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
    
    
	
	
	
}
