package com.simi.vo.resume;

import com.resume.po.model.rule.HrRules;

public class HrRuleVo extends HrRules {
	
	private String fromName;
	
	private String matchDictName;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getMatchDictName() {
		return matchDictName;
	}

	public void setMatchDictName(String matchDictName) {
		this.matchDictName = matchDictName;
	}		
	

}
