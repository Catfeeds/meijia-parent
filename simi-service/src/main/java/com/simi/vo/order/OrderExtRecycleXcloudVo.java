package com.simi.vo.order;

import com.simi.po.model.order.OrderExtRecycle;


public class OrderExtRecycleXcloudVo extends OrderExtRecycle{
	
    
    private Short orderStatus;
    
    private String serviceTypeName;
    
    private String recycleTypeName;
    
    private String addrName;
    
    private Long addrId;
    
    private String orderStatusName;
    
    private String orderExtStatusName;
    
    private Short orderExtStatus;
        
    private String addTimeStr;

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getRecycleTypeName() {
		return recycleTypeName;
	}

	public void setRecycleTypeName(String recycleTypeName) {
		this.recycleTypeName = recycleTypeName;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderExtStatusName() {
		return orderExtStatusName;
	}

	public void setOrderExtStatusName(String orderExtStatusName) {
		this.orderExtStatusName = orderExtStatusName;
	}

	@Override
	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	@Override
	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	
	
}
