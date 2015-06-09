package com.simi.oa.customtags;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;

public class OrderServiceTimeTag extends SimpleTagSupport {

    private Long startTime;
    private int serviceHours;

    public OrderServiceTimeTag() {
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
        	String serviceTimeStr = "";

    		Date startDateTime = TimeStampUtil.timeStampToDateFull(startTime * 1000, "yyyy-MM-dd HH:mm:ss");
    		Long endTimeTimeStamp = startTime + (serviceHours * 3600);
    		Date endDateTime = TimeStampUtil.timeStampToDateFull(endTimeTimeStamp * 1000, "yyyy-MM-dd HH:mm:ss");
    		String startHour = DateUtil.format(startDateTime, "HH:mm");
    		String endHour = DateUtil.format(endDateTime, "HH:mm");

    		serviceTimeStr = startHour;
    		if (serviceHours > 0) {
    			serviceTimeStr = startHour + "-" + endHour;
    		}
            getJspContext().getOut().write(serviceTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public int getServiceHours() {
		return serviceHours;
	}

	public void setServiceHours(int serviceHours) {
		this.serviceHours = serviceHours;
	}

}
