package com.simi.vo.setting;

public class InsuranceBaseVo {

	// json 字段
	private Long cityId; // 城市id
	private String cityName;

	private Long regionId; // 区县id
	private String regionName;

	// 社保基数
	private String shebaoMin; // 下限
	private String shebaoMax; // 上限

	// 公积金基数
	private String gjjMin; // 下限
	private String gjjMax; // 上限

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getShebaoMin() {
		return shebaoMin;
	}

	public void setShebaoMin(String shebaoMin) {
		this.shebaoMin = shebaoMin;
	}

	public String getShebaoMax() {
		return shebaoMax;
	}

	public void setShebaoMax(String shebaoMax) {
		this.shebaoMax = shebaoMax;
	}

	public String getGjjMin() {
		return gjjMin;
	}

	public void setGjjMin(String gjjMin) {
		this.gjjMin = gjjMin;
	}

	public String getGjjMax() {
		return gjjMax;
	}

	public void setGjjMax(String gjjMax) {
		this.gjjMax = gjjMax;
	}

}
