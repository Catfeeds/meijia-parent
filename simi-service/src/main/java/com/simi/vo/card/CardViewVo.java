package com.simi.vo.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;

public class CardViewVo extends Cards {

	private List<CardAttend> attends;
	
	private String createUserName;
	
	private String name;
	
	private int totalZan;
	
	private int totalComment;
	
	private List<CardZanViewVo> zanTop10;

	public List<CardAttend> getAttends() {
		return attends;
	}

	public void setAttends(List<CardAttend> attends) {
		this.attends = attends;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalZan() {
		return totalZan;
	}

	public void setTotalZan(int totalZan) {
		this.totalZan = totalZan;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public List<CardZanViewVo> getZanTop10() {
		return zanTop10;
	}

	public void setZanTop10(List<CardZanViewVo> zanTop10) {
		this.zanTop10 = zanTop10;
	}
}
