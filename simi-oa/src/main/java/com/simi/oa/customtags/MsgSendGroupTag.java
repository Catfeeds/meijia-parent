package com.simi.oa.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class MsgSendGroupTag extends SimpleTagSupport {

	  private Short sendGroupId;

    public MsgSendGroupTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String sendGroupName = "";
        	if (sendGroupId != null) {
        		sendGroupName = MeijiaUtil.getMsgSendGroup(sendGroupId);
        	}
            getJspContext().getOut().write(sendGroupName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getSendGroupId() {
		return sendGroupId;
	}

	public void setSendGroupId(Short sendGroupId) {
		this.sendGroupId = sendGroupId;
	}







}
