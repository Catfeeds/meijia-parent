package com.simi.vo.resume;

import java.util.List;

public class ResumeRuleSearchVo {
	
    private Long fromId;
    
    private Long matchDictId;
    
    private List<Long> matchDictIds;

    private String fileType;
    
    private List<Long> ids;    

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getMatchDictId() {
		return matchDictId;
	}

	public void setMatchDictId(Long matchDictId) {
		this.matchDictId = matchDictId;
	}

	public List<Long> getMatchDictIds() {
		return matchDictIds;
	}

	public void setMatchDictIds(List<Long> matchDictIds) {
		this.matchDictIds = matchDictIds;
	}

}
