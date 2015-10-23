package com.simi.vo.user;

import java.util.List;

import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;

public class UserApplyVo extends Users{

	//是否审批名称
	private String isApprovalName;
	
	//学历名称
	private String degreeName;
	
	private String sexName;

	//用户标签列表
	//private List<Tags> list;
	
	public String getIsApprovalName() {
		return isApprovalName;
	}

	public void setIsApprovalName(String isApprovalName) {
		this.isApprovalName = isApprovalName;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
/*
	public List<Tags> getList() {
		return list;
	}

	public void setList(List<Tags> list) {
		this.list = list;
	}
	*/
	
}
