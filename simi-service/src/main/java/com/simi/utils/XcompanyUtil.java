package com.simi.utils;

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
				staffTypeName = "全职";
		}
		return staffTypeName;
	}
	
	public static Short getStaffType(String staffTypeName) {
		Short staffType = 0;
		switch (staffTypeName) {
			case "全职":
				staffType = 0;
				break;
			case "兼职":
				staffType = 1;
				break;
			case "实习":
				staffType = 2;
				break;
			default:
				staffType =0;
		}
		return staffType;
	}	
}
