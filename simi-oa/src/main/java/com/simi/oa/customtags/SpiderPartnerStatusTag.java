package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import com.meijia.utils.MeijiaUtil;

/**
 * @description：获取spiderPartner的状态
 * @author： kerryg
 * @date:2015年8月6日 
 */
public class SpiderPartnerStatusTag extends SimpleTagSupport {

	  private Short status;

    public SpiderPartnerStatusTag() {
    }
    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String statusName = "";
        	if (status != null) {
        		statusName = MeijiaUtil.getSpiderPartnerStatus(status);
        	}
            getJspContext().getOut().write(statusName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
}
