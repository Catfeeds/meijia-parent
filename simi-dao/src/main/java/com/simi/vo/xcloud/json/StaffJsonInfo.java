package com.simi.vo.xcloud.json;


/*
 *  xcompany_staff 表的 json_info 字段 对应得 对象
 *  
 */
public class StaffJsonInfo {
	
	private String bankCardNo;	//银行卡号
	
	private String bankName;	//开户行
	
	private String contractBeginDate; //合同开始日期
	
	private String contractLimit;  //合同年限
	
	private Long degreeId;
	
	private String degreeName;
	
	private String school;

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getContractBeginDate() {
		return contractBeginDate;
	}

	public void setContractBeginDate(String contractBeginDate) {
		this.contractBeginDate = contractBeginDate;
	}

	public String getContractLimit() {
		return contractLimit;
	}

	public void setContractLimit(String contractLimit) {
		this.contractLimit = contractLimit;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	
}
