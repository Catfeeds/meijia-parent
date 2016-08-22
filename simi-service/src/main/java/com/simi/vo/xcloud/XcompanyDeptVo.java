package com.simi.vo.xcloud;

import java.util.List;

import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;

public class XcompanyDeptVo extends XcompanyDept {
	
	private String companyName;
	private String parentName;
	
	private List<Users> leadUserList;	//负责人列表
	
	private String leadUserName;	//负责人名称
	
	private int total;
	
	
	public String getLeadUserName() {
		return leadUserName;
	}
	public void setLeadUserName(String leadUserName) {
		this.leadUserName = leadUserName;
	}
	
	
	public List<Users> getLeadUserList() {
		return leadUserList;
	}
	public void setLeadUserList(List<Users> leadUserList) {
		this.leadUserList = leadUserList;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
