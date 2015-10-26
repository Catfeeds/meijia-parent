package com.simi.vo.user;

import java.util.List;

import com.simi.po.model.user.TagUsers;
import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;

public class UserApplyVo extends Users{
	
	 //学历名称
	 private String edu;

	//是否审批名称
	private String isApprovalName;
	
	//学历名称
	private String degreeName;
	
	private String sexName;
	
    //用户标签名列表	
	private List<String> tagNamelist;
	
	//标签列表
	
	private List<Tags> tagList;
	
	private String tagNames;
	//接收被点击的 标签的 id
	private String tagId;
	
	
	private String tagIds;
	
	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

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

	public List<String> getTagNamelist() {
		return tagNamelist;
	}

	public void setTagNamelist(List<String> tagNamelist) {
		this.tagNamelist = tagNamelist;
	}

	public List<Tags> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tags> tagList) {
		this.tagList = tagList;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	
	
	
}
