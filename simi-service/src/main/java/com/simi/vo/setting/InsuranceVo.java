package com.simi.vo.setting;

public class InsuranceVo {

	private String cityName;

	private String regionName;

	// json 字段
	private Long cityId; // 城市id
	private Long regionId; // 区县id

	// 个人基数
	private String pensionP; // 养老
	private String medicalP; // 医疗
	private String unemploymentP; // 失业
	private String injuryP; // 工伤
	private String birthP; // 生育
	private String fundP; // 公积金

	// 公司基数
	private String pensionC; // 养老
	private String medicalC; // 医疗
	private String unemploymentC; // 失业
	private String injuryC; // 工伤
	private String birthC; // 生育
	private String fundC; // 公积金

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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getPensionP() {
		return pensionP;
	}

	public void setPensionP(String pensionP) {
		this.pensionP = pensionP;
	}

	public String getMedicalP() {
		return medicalP;
	}

	public void setMedicalP(String medicalP) {
		this.medicalP = medicalP;
	}

	public String getUnemploymentP() {
		return unemploymentP;
	}

	public void setUnemploymentP(String unemploymentP) {
		this.unemploymentP = unemploymentP;
	}

	public String getInjuryP() {
		return injuryP;
	}

	public void setInjuryP(String injuryP) {
		this.injuryP = injuryP;
	}

	public String getBirthP() {
		return birthP;
	}

	public void setBirthP(String birthP) {
		this.birthP = birthP;
	}

	public String getFundP() {
		return fundP;
	}

	public void setFundP(String fundP) {
		this.fundP = fundP;
	}

	public String getPensionC() {
		return pensionC;
	}

	public void setPensionC(String pensionC) {
		this.pensionC = pensionC;
	}

	public String getMedicalC() {
		return medicalC;
	}

	public void setMedicalC(String medicalC) {
		this.medicalC = medicalC;
	}

	public String getUnemploymentC() {
		return unemploymentC;
	}

	public void setUnemploymentC(String unemploymentC) {
		this.unemploymentC = unemploymentC;
	}

	public String getInjuryC() {
		return injuryC;
	}

	public void setInjuryC(String injuryC) {
		this.injuryC = injuryC;
	}

	public String getBirthC() {
		return birthC;
	}

	public void setBirthC(String birthC) {
		this.birthC = birthC;
	}

	public String getFundC() {
		return fundC;
	}

	public void setFundC(String fundC) {
		this.fundC = fundC;
	}

}
