package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class IsReadTag extends SimpleTagSupport {

    private Short isRead;

    public IsReadTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String isReadName = "";
        	if (isRead != null) {
        		isReadName = MeijiaUtil.getIsRead( isRead  );
        	}
            getJspContext().getOut().write(isReadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getIsRead() {
		return isRead;
	}

	public void setIsRead(Short isRead) {
		this.isRead = isRead;
	}







}
