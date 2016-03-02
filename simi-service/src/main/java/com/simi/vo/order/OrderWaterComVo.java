package com.simi.vo.order;

import java.math.BigDecimal;


public class OrderWaterComVo {
		
		private String name;
		
		private Long servicePriceId;
		
		private BigDecimal price;
		
		private BigDecimal disprice;
		
		private String imgUrl;
		
		private String namePrice;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getServicePriceId() {
			return servicePriceId;
		}

		public void setServicePriceId(Long servicePriceId) {
			this.servicePriceId = servicePriceId;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public BigDecimal getDisprice() {
			return disprice;
		}

		public void setDisprice(BigDecimal disprice) {
			this.disprice = disprice;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getNamePrice() {
			return namePrice;
		}

		public void setNamePrice(String namePrice) {
			this.namePrice = namePrice;
		}

}
