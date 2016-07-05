package com.simi.vo.setting;

import com.simi.po.model.xcloud.XcompanySetting;

public class CommonToolsVo extends XcompanySetting {

	private int no;
	
	private int isTop;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getIsTop() {
		return isTop;
	}

	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
}
