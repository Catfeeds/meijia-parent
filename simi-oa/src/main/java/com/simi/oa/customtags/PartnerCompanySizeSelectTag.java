package com.simi.oa.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class PartnerCompanySizeSelectTag extends SimpleTagSupport {

    private String selectId = "0";

    // 是否包含全部，  0 = 不包含  1= 包含
    private String hasAll =  "";

    public PartnerCompanySizeSelectTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {


        	List<String> optionList = MeijiaUtil.getPartnerCompanySize();


            StringBuffer serviceTypeSelect = new StringBuffer();
            serviceTypeSelect.append("<select id = \"companySize\" name=\"companySize\" class=\"form-control\">");

            if (hasAll.equals("")) {
            	serviceTypeSelect.append("<option value='' >全部</option>");
            }

            String item = null;
            String selected = "";
            for(int i = 0;  i<optionList.size();  i++) {
                item = optionList.get(i);
                selected = "";
                if (selectId != null && item.equals(selectId)) {
                	selected = "selected=\"selected\"";
                }
                serviceTypeSelect.append("<option value='" + i + "' " + selected + ">" + item + "</option>");
            }

            serviceTypeSelect.append("</select>");

            getJspContext().getOut().write(serviceTypeSelect.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getHasAll() {
		return hasAll;
	}

	public void setHasAll(String hasAll) {
		this.hasAll = hasAll;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}





}
