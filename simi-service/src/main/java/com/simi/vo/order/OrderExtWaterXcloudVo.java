package com.simi.vo.order;

import java.math.BigDecimal;

import com.simi.po.model.order.OrderExtWater;


public class OrderExtWaterXcloudVo extends OrderExtWater{
	
    
    private Short orderStatus;
    
    private String serviceTypeName;
    
    private String addrName;
    
    private String servicePriceName;
    
    private String imgUrl;
    
    private BigDecimal disPrice;
    
    private BigDecimal orderMoney;
    
    private BigDecimal orderPay;
    
    private String orderStatusName;
    
    private String orderExtStatusName;
    
    private Short orderExtStatus;
    
    private String isDoneTimeStr;
        
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public BigDecimal getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(BigDecimal disPrice) {
		this.disPrice = disPrice;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getOrderPay() {
		return orderPay;
	}

	public void setOrderPay(BigDecimal orderPay) {
		this.orderPay = orderPay;
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

	public String getIsDoneTimeStr() {
		return isDoneTimeStr;
	}

	public void setIsDoneTimeStr(String isDoneTimeStr) {
		this.isDoneTimeStr = isDoneTimeStr;
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

	
}
