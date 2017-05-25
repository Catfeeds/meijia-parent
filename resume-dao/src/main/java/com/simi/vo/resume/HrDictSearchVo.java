package com.simi.vo.resume;

import java.util.List;

public class HrDictSearchVo {
	
    private Long fromId;

    private String type;
    
    private String pid;
    
    private List<String> pids;
    
    private String notCode;
    
    private String orderByStr;
    
	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<String> getPids() {
		return pids;
	}

	public void setPids(List<String> pids) {
		this.pids = pids;
	}

	public String getNotCode() {
		return notCode;
	}

	public void setNotCode(String notCode) {
		this.notCode = notCode;
	}

	public String getOrderByStr() {
		return orderByStr;
	}

	public void setOrderByStr(String orderByStr) {
		this.orderByStr = orderByStr;
	}

}
