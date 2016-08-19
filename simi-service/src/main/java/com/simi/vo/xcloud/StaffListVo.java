package com.simi.vo.xcloud;

import com.simi.po.model.xcloud.XcompanyStaff;


public class StaffListVo extends XcompanyStaff {

	
	//员工类型名称
    private String staffTypeName;
    //部门名称
    private String deptName;
	
	/* 
	 * user 表对应属性 
	 */

    private String mobile;
    // name 无法直接传参
    private String name;	
    
    private String realName;
    
    private String userName;
    
    private String sex;

    private	String headImg;
    
    private String idCard;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
    
 

}
