package com.simi.vo.order;

import com.simi.po.model.order.OrderExtTeam;

public class OrdersTeamAddOaVo extends OrderExtTeam{

    
    private String remarks;
    
    private Long serviceDate;
    
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Long serviceDate) {
		this.serviceDate = serviceDate;
	}
	
	
	
}