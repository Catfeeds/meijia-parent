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
public class PartnerCompanySizeTag extends SimpleTagSupport {

	  private Short companySize;

    public PartnerCompanySizeTag() {
    }
    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String companySizeName = "";
        	if (companySize != null) {
        		companySizeName = MeijiaUtil.getPartnerCompanySize(companySize);
        	}
            getJspContext().getOut().write(companySizeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public Short getCompanySize() {
		return companySize;
	}
	public void setCompanySize(Short companySize) {
		this.companySize = companySize;
	}

	
}
