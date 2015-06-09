package com.simi.oa.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.OneCareUtil;

public class MsgSendGroupSelectTag extends SimpleTagSupport {

    private String selectId = "3";

    // 是否包含全部，  0 = 不包含  1= 包含
    private String hasAll =  "3";

    public MsgSendGroupSelectTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {


        	List<String> optionList =OneCareUtil.getMsgSendGroupName();

            StringBuffer serviceTypeSelect = new StringBuffer();
            serviceTypeSelect.append("<select id = \"msgSendGroupSelect\" name=\"sendGroup\" class=\"form-control\">");

            if (hasAll.equals("3")) {
            	serviceTypeSelect.append("<option value='3' >全部</option>");
            }

            String item = null;
            String selected = "";
            for(int i = 0;  i<optionList.size();  i++) {
                item = optionList.get(i);
                selected = "";
                if (selectId != null && i==Integer.valueOf(selectId)) {
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

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getHasAll() {
		return hasAll;
	}

	public void setHasAll(String hasAll) {
		this.hasAll = hasAll;
	}
	





}
