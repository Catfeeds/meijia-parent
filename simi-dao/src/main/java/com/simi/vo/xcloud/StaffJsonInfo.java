package com.simi.vo.xcloud;

import java.util.Date;

public class StaffJsonInfo {
	
	private String bankCardNo;	//���п���
	
	private String bankName;	//������
	
	private String contractBeginDate; //��ͬ��ʼ����
	
	private String contractLimit;  //��ͬ����

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
	
	
}
