package com.simi.vo.order;

import com.simi.po.model.order.OrderScore;

public class OrderScoreVo extends OrderScore {
	
	private String addTimeStr;
	
	private String orderStatusStr;

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}
}
