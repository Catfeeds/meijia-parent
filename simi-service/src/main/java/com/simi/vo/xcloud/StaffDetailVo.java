package com.simi.vo.xcloud;

import java.util.Date;

import com.simi.po.model.xcloud.XcompanyStaff;


public class StaffDetailVo extends XcompanyStaff {

	
	//员工类型名称
    private String staffTypeName;
    //部门名称
    private String deptName;
	
	/* 
	 * user 表对应属性 
	 */
    private String mobile;

    private String realName;
    
    private String userName;
    
    private String sex;
    private String idCard;	
    private	String headImg;
    
    /*
     * imgs 表对应属性
     */
    
    //身份证照正面
    private String idCardFront;
    //身份证照背面
    private String idCardBack;	
    
    //毕业证照片
    private String imgDegree;
    
    /*
     * 封装成 json 格式的字段 ,加入vo
     */
	private String bankCardNo;	//银行卡号
	
	private String bankName;	//开户行
	
	private String contractBeginDate; //合同开始日期
	
	private String contractLimit;  //合同期限
	
	private Long degreeId;
	
	private String degreeName;
	
	private String school;

	public String getStaffTypeName() {
		return staffTypeName;
	}

	public void setStaffTypeName(String staffTypeName) {
		this.staffTypeName = staffTypeName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

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

	public String getContractLimit() {
		return contractLimit;
	}

	public void setContractLimit(String contractLimit) {
		this.contractLimit = contractLimit;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getContractBeginDate() {
		return contractBeginDate;
	}

	public void setContractBeginDate(String contractBeginDate) {
		this.contractBeginDate = contractBeginDate;
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

	public String getImgDegree() {
		return imgDegree;
	}

	public void setImgDegree(String imgDegree) {
		this.imgDegree = imgDegree;
	}
}
