package com.simi.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 云行政的常用方法
 *
 */
public class OrderUtil {

	public static String getOrderStausName(Short status) {
		String statusName = "";
		switch (status) {
			case 0:
				statusName = "已取消";
				break;
			case 1:
				statusName = "待支付";
				break;
			case 2:
				statusName = "已支付";
				break;
			case 3:
				statusName = "处理中";
				break;
			case 7:
				statusName = "待评价";
				break;
			case 8:
				statusName = "已完成";
				break;	
			case 9:
				statusName = "已关闭";
				break;
			default:
				statusName = "";
		}
		return statusName;
	}
	
	public static String getOrderStausMsg(Short status) {
		String statusName = "";
		switch (status) {
			case 0:
				statusName = "你的订单已取消.";
				break;
			case 1:
				statusName = "你生成了新的待支付订单.";
				break;
			case 2:
				statusName = "你的订单已经支付成功.";
				break;
			case 3:
				statusName = "你的订单正在处理中.";
				break;
			case 7:
				statusName = "你的订单已经完成，请对我们的服务进行评价.";
				break;
			case 8:
				statusName = "你的订单已经完成.";
				break;	
			case 9:
				statusName = "你的订单已经完成已关闭.";
				break;
			default:
				statusName = "";
		}
		return statusName;
	}	
	
	public static String getStatusName(Short status) {
		String statusName = "";
		switch (status) {
		case 0:
			statusName = "无效";
			break;
		case 1:
			statusName = "有效";
			break;
		default:
			statusName = "";
		}
		return statusName;
	}
	
	public static List<String> getOrderStatus(){
		List<String> list = new ArrayList<String>();
		list.add(0, "已取消");
		list.add(1, "待支付");
		list.add(2, "已支付");
		list.add(3, "处理中");
		list.add(7, "待评价");
		list.add(8, "已完成");
		list.add(9, "已关闭");
		return list;
	}
	
	public static String getOrderFromName(Short status) {
		String statusName = "";
		switch (status) {
			case 0:
				statusName = "App";
				break;
			case 1:
				statusName = "微网站";
				break;
			case 2:
				statusName = "管理平台";
				break;
			case 3:
				statusName = "好友邀请";
				break;				
			case 999:
				statusName = "所用来源";
				break;
			default:
				statusName = "";
		}
		return statusName;
	}

	public static List<String> getOrderFrom(){
		List<String> list = new ArrayList<String>();
		list.add(0,"APP");
		list.add(1,"微网站");
		list.add(2,"管理平台");
		return list;
	}
	
	public static String getPayTypeName(Short payType) {
		String payTypeName = "";
		switch (payType) {
			case 0:
				payTypeName = "余额支付";
				break;
			case 1:
				payTypeName = "支付宝支付";
				break;
			case 2:
				payTypeName = "微信支付";
				break;
			case 3:
				payTypeName = "智慧支付";
				break;
			case 4:
				payTypeName = "上门刷卡";
				break;
			case 5:
				payTypeName ="优惠券兑换";
				break;
			default:
				payTypeName = "";
		}
		return payTypeName;
	}

	public static String getOrderTypeName(Short orderTypeId) {
		String orderTypeName = "";
		switch (orderTypeId) {
		case 0:
			orderTypeName = "订单支付";
			break;
		case 1:
			orderTypeName = "购买充值卡";
			break;
		case 2:
			orderTypeName = "购买管家卡";
			break;
		case 3:
			orderTypeName = "订单退款";
			break;
		default:
			orderTypeName = "";
		}
		return orderTypeName;
	}
	
	public static String getOrderCleaAreaName(Short cleaArea) {
		String cleaAreaName = "";
		switch (cleaArea) {
		case 0:
			cleaAreaName = "0-300平米";
			break;
		case 1:
			cleaAreaName = "300-800平米";
			break;
		case 2:
			cleaAreaName = "800平米以上";
			break;
		default:
			cleaAreaName = "";
		}
		return cleaAreaName;
	}	
	
	public static String getOrderCleaTypeName(Short cleaType) {
		String cleaTypeName = "";
		switch (cleaType) {
		case 0:
			cleaTypeName = "定期保洁";
			break;
		case 1:
			cleaTypeName = "深度养护";
			break;
		case 2:
			cleaTypeName = "维修清洗";
			break;
		case 3:
			cleaTypeName = "其他";
			break;
		default:
			cleaTypeName = "";
		}
		return cleaTypeName;
	}
	public static String getOrderRecycleTypeName(Short rectcleType) {
		String rectcleTypeName = "";
		switch (rectcleType) {
		case 0:
			rectcleTypeName = "日常办公垃圾";
			break;
		case 1:
			rectcleTypeName = "废旧电器";
			break;
		case 2:
			rectcleTypeName = "硒鼓墨盒";
			break;
		case 3:
			rectcleTypeName = "其他";
			break;
		default:
			rectcleTypeName = "";
		}
		return rectcleTypeName;
	}
	
	public static String getOrderTeamTypeName(Short teamType) {
		String teamTypeName = "";
		switch (teamType) {
		case 0:
			teamTypeName = "不限";
			break;
		case 1:
			teamTypeName = "年会";
			break;
		case 2:
			teamTypeName = "拓展训练";
			break;
		case 3:
			teamTypeName = "聚会沙龙";
			break;
		case 4:
			teamTypeName = "休闲度假";
			break;
		case 5:
			teamTypeName = "其他";
			break;
		default:
			teamTypeName = "";
		}
		return teamTypeName;
	}
}
