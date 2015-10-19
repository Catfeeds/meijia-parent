package com.simi.oa.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;

public class NationTypeSelectTag extends SimpleTagSupport {

    private String nationId ;

    public NationTypeSelectTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {

        		List<String> optionList = MeijiaUtil.getNationType();

            StringBuffer serviceTypeSelect = new StringBuffer();
            serviceTypeSelect.append("<select id = \"nationId\" name=\"nation\" class=\"form-control\">");

            String item = null;
            String selected = "";
            for(int i = 0;  i<optionList.size();  i++) {
                item = optionList.get(i);
                selected = "";
                if (nationId != null && i==Integer.valueOf(nationId)) {
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

	public String getNationId() {
		return nationId;
	}

	public void setNationId(String nationId) {
		this.nationId = nationId;
	}

}
