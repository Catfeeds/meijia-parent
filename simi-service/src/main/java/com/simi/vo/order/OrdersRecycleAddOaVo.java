package com.simi.vo.order;

import com.simi.po.model.order.OrderExtRecycle;

public class OrdersRecycleAddOaVo extends OrderExtRecycle{

    
    private String remarks;
    
    private Long serviceDate;
    
    private Long addrId;
    
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

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	
}