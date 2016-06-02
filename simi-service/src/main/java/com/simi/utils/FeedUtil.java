package com.simi.utils;


public class FeedUtil {

	public static String getFeedTypeName(Short feedType) {
		String name = "";
		switch (feedType) {
			case 0:
				name = "动态";
				break;
			case 1:
				name = "文章";
				break;
			case 2:
				name = "问答";
				break;
			
			default:
				name = "";
		}
		return name;
	}
}
