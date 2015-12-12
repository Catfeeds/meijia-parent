package com.meijia.utils.baidu.vo;

public class BaiduGeoCoderResultVo {
	private String formatted_address;
	
	private String business;
	
	private BaiduGeoCoderAddressComponent addressComponent;
	
	private String sematic_description;
	
	private String cityCode;

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public BaiduGeoCoderAddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(BaiduGeoCoderAddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public String getSematic_description() {
		return sematic_description;
	}

	public void setSematic_description(String sematic_description) {
		this.sematic_description = sematic_description;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
