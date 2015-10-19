package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class RangTypeNameTag extends SimpleTagSupport {

    private Short rangType;

    public RangTypeNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String rangTypeName = "";
        	if (rangType != null) {
        		rangTypeName = MeijiaUtil.getRangTypeName( rangType  );
        	}
            getJspContext().getOut().write(rangTypeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getRangType() {
		return rangType;
	}

	public void setRangType(Short rangType) {
		this.rangType = rangType;
	}


}
