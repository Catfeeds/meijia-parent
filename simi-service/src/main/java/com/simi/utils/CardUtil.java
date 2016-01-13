package com.simi.utils;

import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;

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
				cardTypeName = "通知公告";
				break;
			case 3:
				cardTypeName = "事务提醒";
				break;
			case 4:
				cardTypeName = "面试邀约";
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
			break;
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
		
		String timeStr = TimeStampUtil.timeStampToDateStr(serviceTime * 1000, "MM月dd日HH:mm");
		
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
//				cardTypeName = "通知公告";
				remindContent = "秘书叫您" + timeStr + "起床啦！";
				break;
			case 3:
//				cardTypeName = "事务提醒";
				remindContent = timeStr + "即将发生" + cardTypeName;
				break;
			case 4:
//				cardTypeName = "面试邀约";
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
	
	
	public static String getCardLogo(Short cardType) {
		
		String imgUrl = "";
		
		switch (cardType) {
			case 0:
				imgUrl = Constants.IMG_SERVER_HOST + "/cb5e6bc8f19eddc3081909b6f9ae69df";
				break;
			case 1:
//				cardTypeName = "会议安排";
				imgUrl = Constants.IMG_SERVER_HOST + "/cb5e6bc8f19eddc3081909b6f9ae69df";
				break;
			case 2:
//				cardTypeName = "通知公告";
				imgUrl = Constants.IMG_SERVER_HOST + "/092a77928064df50c56faa5f25a78f8c";
				break;
			case 3:
//				cardTypeName = "事务提醒";
				imgUrl = Constants.IMG_SERVER_HOST + "/46d444bb6ee1bb611d82bdf7d766381f";
				break;
			case 4:
//				cardTypeName = "面试邀约";
				imgUrl = Constants.IMG_SERVER_HOST + "/bf0fbd4fc52904ab17b57c0be802097e";
				break;
			case 5:
//				cardTypeName = "差旅规划";
				imgUrl = Constants.IMG_SERVER_HOST + "/6aceede35dbd350a3b094b8b64629bce";
				break;
			default:
				imgUrl = "";
		}		
	
		return imgUrl;
	}		
	
	public static String getLabelAttendStr(Short cardType) {
		String labelAttendStr = "";
		switch (cardType) {
			case 0:
			case 2:
			case 4:
			case 5:
//				cardTypeName = "通用";
				labelAttendStr = "接收人";
				break;
			case 1:
//				cardTypeName = "会议安排";
				labelAttendStr = "参会人员";
				break;
			case 3:
//				cardTypeName = "事务提醒";
				labelAttendStr = "提醒人员";
				break;
			default:
//				cardTypeName = "";
				labelAttendStr = "接收人";
		}
		return labelAttendStr;
	}		
	
	public static String getLabelTimeStr(Short cardType) {
		String labelTimeStr = "";
		switch (cardType) {
			case 0:
			case 2:
			case 3:
			case 4:
//				cardTypeName = "通用";
				labelTimeStr = "提醒时间";
				break;
			case 1:
//				cardTypeName = "会议安排";
				labelTimeStr = "会议时间";
				break;
			case 5:
//				cardTypeName = "差旅规划";
				labelTimeStr = "出发时间";
				break;
			default:
//				cardTypeName = "";
				labelTimeStr = "提醒时间";
		}
		return labelTimeStr;
	}	
	
	public static String getLabelContentStr(Short cardType) {
		String labelContentStr = "";
		switch (cardType) {
			case 0:
			case 2:
			case 3:
			case 4:
//				cardTypeName = "通用";
				labelContentStr = "提醒内容";
				break;
			case 1:
//				cardTypeName = "会议安排";
				labelContentStr = "会议内容";
				break;
			case 5:
//				cardTypeName = "差旅规划";
				labelContentStr = "差旅内容";
				break;
			default:
//				cardTypeName = "";
				labelContentStr = "提醒内容";
		}
		return labelContentStr;
	}		
	
	public static String getCardTips(Short cardType) {
		
		String tips = "";
		
		switch (cardType) {
			case 0:
				tips = "";
				break;
			case 1:
//				cardTypeName = "会议安排";
				tips = "发起会议通知同事，可创建这个卡片哦，不仅能给自己提醒，还能发送或分享给好友，快来试试吧~";
				break;
			case 2:
//				cardTypeName = "通知公告";
				tips = "发布公司日常的通知公告，可创建这个卡片哦，不仅能给自己提醒，还能发送或分享给好友，快来试试吧~";
				break;
			case 3:
//				cardTypeName = "事务提醒";
				tips = "重要的事情需要提醒，可创建这个卡片哦，不仅能给自己提醒，还能发送或分享给好友，快来试试吧~";
				break;
			case 4:
//				cardTypeName = "面试邀约";
				tips = "邀约面试者，可创建这个卡片哦，不仅能给自己提醒，还能发送或分享给好友，快来试试吧~";
				break;
			case 5:
//				cardTypeName = "差旅规划";
				tips = "商务差旅的安排与提醒，可创建这个卡片哦，不仅能给自己提醒，还能发送或分享给好友，快来试试吧~";
				break;
			default:
				tips = "";
		}		
	
		return tips;
	}			
	
}
