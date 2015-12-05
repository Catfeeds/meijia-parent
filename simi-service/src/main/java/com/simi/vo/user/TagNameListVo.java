package com.simi.vo.user;

import java.util.List;

import com.simi.po.model.user.Tags;
import com.simi.po.model.user.Users;

public class TagNameListVo extends Users{
	//标签集合List
	private List<Tags> list;
	//标签idList
	private List<Long> tagList;
	//学历类型集合
//	private List<String> DegreeTypeList;
	
	public List<Tags> getList() {
		return list;
	}
	public void setList(List<Tags> list) {
		this.list = list;
	}
	public List<Long> getTagList() {
		return tagList;
	}
	public void setTagList(List<Long> tagList) {
		this.tagList = tagList;
	}
/*	public List<String> getDegreeTypeList() {
		return DegreeTypeList;
	}
	public void setDegreeTypeList(List<String> degreeTypeList) {
		DegreeTypeList = degreeTypeList;
	}*/


	
	
}
