package com.simi.vo.xcloud.json;


/**
 * 
* @author hulj 
* @date 2016年6月14日 下午2:31:49 
* @Description: 
* 
* 	针对 xcompany_setting 表 中  json 字段  settting_value 对应的类
*
 */
public class SettingJsonSettingValue {
	
	private String  cityId;		// 城市id
	private String  regionId;	//区县id
	
	private String	pension;	//养老 
	private String	medical;	//医疗 
	private String	unemployment;	//失业 
	private String	injury;		//工伤 
	private String	birth;		//生育 
	private String	fund;		//公积金 
	
	
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
	  
	
}
