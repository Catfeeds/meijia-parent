package com.simi.vo.user;

import com.simi.po.model.user.UserDetailScore;

public class UserDetailScoreVo extends UserDetailScore {
	
	private String scoreStr;

	private String actionName;

    private String addTimeStr;


	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getScoreStr() {
		return scoreStr;
	}

	public void setScoreStr(String scoreStr) {
		this.scoreStr = scoreStr;
	}
    
    
	
	
	
}
