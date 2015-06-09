package com.simi.vo.order;

public class OrdersVo {

	    private String mobile;

	    private Long cityId;

	    private String orderNo;

	    private Short serviceType;

	    private Long serviceDate;

	    private Long startTime;

	    private Short serviceHours;

	    private Long cellId;

	    private Long addrId;

	    private String remarks;

	    private Short orderFrom;

	    private Short orderStatus;

	    private Short orderRate;

	    private Short isScore;

	    private String orderRateContent;

	    private Long addTime;

	    private Long updateTime;

	    private String userName;

	    private String userAddrs;

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public Long getCityId() {
			return cityId;
		}

		public void setCityId(Long cityId) {
			this.cityId = cityId;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public Short getServiceType() {
			return serviceType;
		}

		public void setServiceType(Short serviceType) {
			this.serviceType = serviceType;
		}

		public Long getServiceDate() {
			return serviceDate;
		}

		public void setServiceDate(Long serviceDate) {
			this.serviceDate = serviceDate;
		}

		public Long getStartTime() {
			return startTime;
		}

		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}

		public Short getServiceHours() {
			return serviceHours;
		}

		public void setServiceHours(Short serviceHours) {
			this.serviceHours = serviceHours;
		}

		public Long getCellId() {
			return cellId;
		}

		public void setCellId(Long cellId) {
			this.cellId = cellId;
		}

		public Long getAddrId() {
			return addrId;
		}

		public void setAddrId(Long addrId) {
			this.addrId = addrId;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public Short getOrderFrom() {
			return orderFrom;
		}

		public void setOrderFrom(Short orderFrom) {
			this.orderFrom = orderFrom;
		}

		public Short getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(Short orderStatus) {
			this.orderStatus = orderStatus;
		}

		public Short getOrderRate() {
			return orderRate;
		}

		public void setOrderRate(Short orderRate) {
			this.orderRate = orderRate;
		}

		public Short getIsScore() {
			return isScore;
		}

		public void setIsScore(Short isScore) {
			this.isScore = isScore;
		}

		public String getOrderRateContent() {
			return orderRateContent;
		}

		public void setOrderRateContent(String orderRateContent) {
			this.orderRateContent = orderRateContent;
		}

		public Long getAddTime() {
			return addTime;
		}

		public void setAddTime(Long addTime) {
			this.addTime = addTime;
		}

		public Long getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Long updateTime) {
			this.updateTime = updateTime;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserAddrs() {
			return userAddrs;
		}

		public void setUserAddrs(String userAddrs) {
			this.userAddrs = userAddrs;
		}

		public OrdersVo(String mobile, Long cityId, String orderNo,
				Short serviceType, Long serviceDate, Long startTime,
				Short serviceHours, Long cellId, Long addrId, String remarks,
				Short orderFrom, Short orderStatus, Short orderRate,
				Short isScore, String orderRateContent, Long addTime,
				Long updateTime, String userName, String userAddrs) {
			super();
			this.mobile = mobile;
			this.cityId = cityId;
			this.orderNo = orderNo;
			this.serviceType = serviceType;
			this.serviceDate = serviceDate;
			this.startTime = startTime;
			this.serviceHours = serviceHours;
			this.cellId = cellId;
			this.addrId = addrId;
			this.remarks = remarks;
			this.orderFrom = orderFrom;
			this.orderStatus = orderStatus;
			this.orderRate = orderRate;
			this.isScore = isScore;
			this.orderRateContent = orderRateContent;
			this.addTime = addTime;
			this.updateTime = updateTime;
			this.userName = userName;
			this.userAddrs = userAddrs;
		}

		public OrdersVo() {
			super();
		}




}
