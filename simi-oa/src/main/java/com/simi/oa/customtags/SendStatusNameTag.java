package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.OneCareUtil;

public class SendStatusNameTag extends SimpleTagSupport {

    private Short sendStatus;

    public SendStatusNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String sendStatusName = "";
        	if (sendStatus != null) {
        		sendStatusName = OneCareUtil.getSendStatusName( sendStatus  );
        	}
            getJspContext().getOut().write(sendStatusName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Short sendStatus) {
		this.sendStatus = sendStatus;
	}

	




}
