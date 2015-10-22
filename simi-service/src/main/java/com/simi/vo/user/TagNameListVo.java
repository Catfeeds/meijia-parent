package com.simi.vo.user;

import java.util.List;

import com.simi.po.model.user.Tags;

public class TagNameListVo {
	//标签集合List
	private List<Tags> list;
	//标签idList
	private List<Long> tagList;
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


	
	
}
