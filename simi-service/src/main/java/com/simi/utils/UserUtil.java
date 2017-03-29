package com.simi.utils;

import java.util.Date;

import com.meijia.utils.DateUtil;
import com.meijia.utils.TimeStampUtil;
import com.meijia.utils.Week;
import com.simi.common.Constants;

/**
 * 用户常用静态方法
 *
 */
public class UserUtil {

	public static String getScoreActionName(String action) {
		String actionName = "";
		switch (action) {
			case "new_user":
				actionName = "新用户注册";
				break;
			case "day_sign":
				actionName = "签到";
				break;
			case "leave":
				actionName = "请假申请";
				break;
			case "checkin":
				actionName = "云考勤";
				break;	
			case "order":
				actionName = "订单";
				break;	
			case "qa":
				actionName = "问答相关";
				break;	
				
			case "company_reg":
				actionName = "创建企业/团队";
				break;		
				
			case "duiba":
				actionName = "兑吧";
				break;		
				
			case "alarm":
			case "notice":
			case "interview":
			case "meeting":
			case "trip":
				actionName = "创建卡牌";
				break;		
			
			default:
				actionName = "";
		}
		return actionName;
	}	
	

}
