package com.simi.vo.order;

import com.simi.po.model.order.OrderExtWater;

public class OrdersWaterAddOaVo extends OrderExtWater{

    
    private String remarks;
    
    private Long addrId;
    
    private String mobile;
    
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}