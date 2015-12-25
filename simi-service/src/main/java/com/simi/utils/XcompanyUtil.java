package com.simi.utils;

import com.meijia.utils.TimeStampUtil;
import com.simi.common.Constants;

/**
 * 公司的常用静态方法
 *
 */
public class XcompanyUtil {

	public static String getStaffTypeName(Short staffType) {
		String staffTypeName = "";
		switch (staffType) {
			case 0:
				staffTypeName = "全职";
				break;
			case 1:
				staffTypeName = "兼职";
				break;
			case 2:
				staffTypeName = "实习";
				break;
			default:
				staffTypeName = "";
		}
		return staffTypeName;
	}
}
