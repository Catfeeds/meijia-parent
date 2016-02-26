package com.simi.po.model.order;


public class OrderExtGreen {
	
	private Long id;
	
	private Long orderId;
    
    private String orderNo;
    
    private Long userId;
    
    private String mobile;
    
    private Long totalNum;
    
    private Long totalBudget;
    
    private Long addTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}



	public Long getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(Long totalBudget) {
		this.totalBudget = totalBudget;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
}