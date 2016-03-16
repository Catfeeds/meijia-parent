package com.simi.vo.order;

import com.simi.po.model.order.OrderExtTeam;


public class OrderExtTeamXcloudVo extends OrderExtTeam{
	
    
    private Short orderStatus;
    
    private String serviceTypeName;
    
    private String teamTypeName;
    
    private String cityName;
    
    private Long cityId;
    
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

	

	public String getTeamTypeName() {
		return teamTypeName;
	}

	public void setTeamTypeName(String teamTypeName) {
		this.teamTypeName = teamTypeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	@Override
	public Short getOrderExtStatus() {
		return orderExtStatus;
	}

	@Override
	public void setOrderExtStatus(Short orderExtStatus) {
		this.orderExtStatus = orderExtStatus;
	}

	public String getOrderExtStatusName() {
		return orderExtStatusName;
	}

	public void setOrderExtStatusName(String orderExtStatusName) {
		this.orderExtStatusName = orderExtStatusName;
	}

	@Override
	public Long getCityId() {
		return cityId;
	}

	@Override
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	
}
