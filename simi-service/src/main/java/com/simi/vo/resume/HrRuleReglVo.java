package com.simi.vo.resume;

public class HrRuleReglVo {

	private String blockRegex;

	private int blockMatchIndex;

	private String fieldRegex;

	private String fieldMatchIndex;
	
	private String matchCorrent;
	
	private Long matchDictTypeId;

	public String getBlockRegex() {
		return blockRegex;
	}

	public void setBlockRegex(String blockRegex) {
		this.blockRegex = blockRegex;
	}

	public int getBlockMatchIndex() {
		return blockMatchIndex;
	}

	public void setBlockMatchIndex(int blockMatchIndex) {
		this.blockMatchIndex = blockMatchIndex;
	}

	public String getFieldRegex() {
		return fieldRegex;
	}

	public void setFieldRegex(String fieldRegex) {
		this.fieldRegex = fieldRegex;
	}

	public String getFieldMatchIndex() {
		return fieldMatchIndex;
	}

	public void setFieldMatchIndex(String fieldMatchIndex) {
		this.fieldMatchIndex = fieldMatchIndex;
	}

	public String getMatchCorrent() {
		return matchCorrent;
	}

	public void setMatchCorrent(String matchCorrent) {
		this.matchCorrent = matchCorrent;
	}

	public Long getMatchDictTypeId() {
		return matchDictTypeId;
	}

	public void setMatchDictTypeId(Long matchDictTypeId) {
		this.matchDictTypeId = matchDictTypeId;
	}

}
