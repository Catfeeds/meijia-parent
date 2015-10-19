package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class UserTypeNameTag extends SimpleTagSupport {

	  private Short userTypeId;

    public UserTypeNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String userTypeName = "";
        	if (userTypeId != null) {
        		userTypeName = MeijiaUtil.getUserTypeName(userTypeId);
        	}
            getJspContext().getOut().write(userTypeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Short userTypeId) {
		this.userTypeId = userTypeId;
	}


}
