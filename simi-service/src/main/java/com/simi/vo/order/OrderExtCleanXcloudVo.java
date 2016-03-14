package com.simi.vo.order;

import com.simi.po.model.order.OrderExtClean;


public class OrderExtCleanXcloudVo extends OrderExtClean{
	
    
    private Short orderStatus;
    
    private String serviceTypeName;
    
    private String cleanTypeName;
    
    private String addrName;
    
    private String servicePriceName;
    
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

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public String getServicePriceName() {
		return servicePriceName;
	}

	public void setServicePriceName(String servicePriceName) {
		this.servicePriceName = servicePriceName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}

	public String getOrderExtStatusName() {
		return orderExtStatusName;
	}

	public void setOrderExtStatusName(String orderExtStatusName) {
		this.orderExtStatusName = orderExtStatusName;
	}

	public String getCleanTypeName() {
		return cleanTypeName;
	}

	public void setCleanTypeName(String cleanTypeName) {
		this.cleanTypeName = cleanTypeName;
	}

	
}
