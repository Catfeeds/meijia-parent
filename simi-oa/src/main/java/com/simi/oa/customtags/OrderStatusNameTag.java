package com.simi.oa.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.MeijiaUtil;
import com.simi.utils.OrderUtil;

public class OrderStatusNameTag extends SimpleTagSupport {

    private Short orderStatus;

    public OrderStatusNameTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String orderStatusName = "";
        	if (orderStatus != null) {
        		orderStatusName = OrderUtil.getOrderStausName( orderStatus  );
        	}
            getJspContext().getOut().write(orderStatusName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

}
