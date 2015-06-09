package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.OneCareUtil;

public class IsUsedNameTag extends SimpleTagSupport {

    private Short isUsed;

    public IsUsedNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String isUsedName = "";
        	if (isUsed != null) {
        		isUsedName = OneCareUtil.getIsUsedName( isUsed  );
        	}
            getJspContext().getOut().write(isUsedName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Short isUsed) {
		this.isUsed = isUsed;
	}










}
