package com.simi.oa.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.simi.utils.OrderUtil;

public class OrderTypeNameTag extends SimpleTagSupport {

	  private Short orderTypeId;

    public OrderTypeNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String orderTypeName = "";
        	if (orderTypeId != null) {
        		orderTypeName = OrderUtil.getOrderTypeName(orderTypeId);
        	}
            getJspContext().getOut().write(orderTypeName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Short orderTypeId) {
		this.orderTypeId = orderTypeId;
	}



}
