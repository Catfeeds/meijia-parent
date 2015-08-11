package com.simi.oa.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.OneCareUtil;

public class SpiderPartnerServiceTypeSelectTag extends SimpleTagSupport {

    private String selectId = "0";

    // 是否包含全部，  0 = 不包含  1= 包含
    private String hasAll =  "8";

    public SpiderPartnerServiceTypeSelectTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {


        	List<String> optionList = OneCareUtil.getSpiderPartnerServiceType();


            StringBuffer serviceTypeSelect = new StringBuffer();
            serviceTypeSelect.append("<select id = \"serviceType\" name=\"serviceType\" class=\"form-control\">");

            if (hasAll.equals("8")) {
            	serviceTypeSelect.append("<option value='全部' >全部</option>");
            }

            String item = null;
            String selected = "";
            for(int i = 0;  i<optionList.size();  i++) {
                item = optionList.get(i);
                selected = "";
                if (selectId != null && item.equals(selectId)) {
                	selected = "selected=\"selected\"";
                }
                serviceTypeSelect.append("<option value='" + item+ "' " + selected + ">" + item + "</option>");
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
