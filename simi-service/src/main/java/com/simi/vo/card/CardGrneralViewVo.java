package com.simi.vo.card;

import java.util.List;

import com.simi.po.model.card.CardAttend;
import com.simi.po.model.card.Cards;

public class CardGrneralViewVo extends Cards {

	
	
	private String createUserName;
	
	//用户姓名
	private String name ;
	
	private String cartTypeName;
	
	private List<CardAttend> attends;

	private String addTimeStr;

	


	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCartTypeName() {
		return cartTypeName;
	}

	public void setCartTypeName(String cartTypeName) {
		this.cartTypeName = cartTypeName;
	}

	public List<CardAttend> getAttends() {
		return attends;
	}

	public void setAttends(List<CardAttend> attends) {
		this.attends = attends;
	}

	
}
