package com.simi.vo.xcloud;

import java.util.List;

import com.simi.po.model.user.Users;
import com.simi.po.model.xcloud.XcompanyDept;
import com.simi.po.model.xcloud.XcompanyJob;

public class XcompanyJobVo extends XcompanyJob {
	
	private String deptName;
	
	private List<Users> userList;	
	
	private List<XcompanyDept> deptList;
	
	public List<XcompanyDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<XcompanyDept> deptList) {
		this.deptList = deptList;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
