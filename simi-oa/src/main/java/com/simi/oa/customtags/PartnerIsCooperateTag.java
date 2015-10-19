package com.simi.oa.customtags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import com.meijia.utils.MeijiaUtil;


/**
 * @description：
 * @author： kerryg
 * @date:2015年8月11日 
 */
public class PartnerIsCooperateTag extends SimpleTagSupport {

	  private Short isCooperate;

    public PartnerIsCooperateTag() {
    }
    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String isCooperateName = "";
        	if (isCooperate != null) {
        		isCooperateName = MeijiaUtil.getPartnerIsCooperate(isCooperate);
        	}
            getJspContext().getOut().write(isCooperateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Short getIsCooperate() {
		return isCooperate;
	}
	public void setIsCooperate(Short isCooperate) {
		this.isCooperate = isCooperate;
	}

	
}
