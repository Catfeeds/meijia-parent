package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanySetting;
import com.simi.vo.xcloud.json.SettingJsonSettingValue;


public class CompanySettingVo extends XcompanySetting {

	private Long settingId;
	
	//添加时间戳
	private String addTimeStr ;
	
	
	private String cityName;
	
	private String regionName;
	
	//json 字段
	private String  cityId;		// 城市id
	private String  regionId;	//区县id
	
	private String	pension;	//养老 
	private String	medical;	//医疗 
	private String	unemployment;	//失业 
	private String	injury;		//工伤 
	private String	birth;		//生育 
	private String	fund;		//公积金
	
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getPension() {
		return pension;
	}
	public void setPension(String pension) {
		this.pension = pension;
	}
	public String getMedical() {
		return medical;
	}
	public void setMedical(String medical) {
		this.medical = medical;
	}
	public String getUnemployment() {
		return unemployment;
	}
	public void setUnemployment(String unemployment) {
		this.unemployment = unemployment;
	}
	public String getInjury() {
		return injury;
	}
	public void setInjury(String injury) {
		this.injury = injury;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public Long getSettingId() {
		return settingId;
	}
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}
	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
	
}
