package com.simi.utils;

import com.meijia.utils.TimeStampUtil;

/**
 * 卡片的常用静态方法
 *
 */
public class CardUtil {

	public static String getCardTypeName(Short cardType) {
		String cardTypeName = "";
		switch (cardType) {
			case 0:
				cardTypeName = "通用";
				break;
			case 1:
				cardTypeName = "会议安排";
				break;
			case 2:
				cardTypeName = "秘书叫早";
				break;
			case 3:
				cardTypeName = "事务提醒";
				break;
			case 4:
				cardTypeName = "邀约通知";
				break;
			case 5:
				cardTypeName = "差旅规划";
				break;
			default:
				cardTypeName = "";
		}
		return cardTypeName;
	}	
	
	/**
	 * 获取提醒设置的提前分钟数
	 * 提醒设置 
		0 = 不提醒 
		1 = 按时提醒 
		2 = 5分钟 
		3 = 15分钟 
		4 = 提前30分钟
		5 = 提前一个小时
		6 = 提前2小时
		7 = 提前6小时
		8 = 提前一天
		9 = 提前两天
	 */
	
	public static int getRemindMin(Short setRemind) {
		
		int remindMin = 0;
		
		switch (setRemind) {
		case 0:
			remindMin = 0;
			break;
		case 1:
			remindMin = 0;
			break;
		case 2:
			remindMin = 5;
		case 3:
			remindMin = 15;	
			break;
		case 4:
			remindMin = 30;	
			break;
		case 5:
			remindMin = 60;	
			break;
		case 6:
			remindMin = 2 * 60;	
			break;
		case 7:
			remindMin = 6 * 60;	
			break;
		case 8:
			remindMin = 24 * 60;	
			break;
		case 9:
			remindMin = 48 * 60;	
			break;	
		default:
			remindMin = 0;
		}
	
		return remindMin;
	}
	
	public static String getStatusName(Short status) {
		
		String statusName = "";
		
		switch (status) {
		case 0:
			statusName = "已取消";
			break;
		case 1:
			statusName = "处理中";
			break;
		case 2:
			statusName = "秘书处理中";
		case 3:
			statusName = "完成";	
			break;
		default:
			statusName = "";
		}
	
		return statusName;
	}	
	
	public static String getRemindContent(Short cardType, Long serviceTime) {
		
		String remindContent = "";
		
		String timeStr = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "yyyy年MM月dd日HH:00");
		
		String cardTypeName = getCardTypeName(cardType);
		
		switch (cardType) {
			case 0:
				remindContent = timeStr + "即将发生" + cardTypeName;
				break;
			case 1:
//				cardTypeName = "会议安排";
				remindContent = timeStr + "即将发生" + cardTypeName;
				break;
			case 2:
//				cardTypeName = "秘书叫早";
				remindContent = "秘书叫您" + timeStr + "起床啦！";
				break;
			case 3:
//				cardTypeName = "事务提醒";
				remindContent = timeStr + "即将发生" + cardTypeName;
				break;
			case 4:
//				cardTypeName = "邀约通知";
				remindContent = timeStr + "即将发生邀约安排";
				break;
			case 5:
//				cardTypeName = "差旅规划";
				remindContent = timeStr + "即将发生差旅事务安排";
				break;
			default:
				cardTypeName = "";
		}		
	
		return remindContent;
	}	
	
}
