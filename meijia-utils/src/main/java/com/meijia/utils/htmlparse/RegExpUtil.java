package com.meijia.utils.htmlparse;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpUtil {
	/**
	 * 匹配一次
	 * @author 董光明
	 * @date 2016年5月5日
	 * @param regex 正则表达式
	 * @param input 输入的原字符串
	 * @param flag 正则编译模式
	 * 	Pattern.CANON_EQ
	 *  Pattern.CASE_INSENSITIVE
	 *  Pattern.COMMENTS
	 *  Pattern.DOTALL
	 *  Pattern.MULTILINE
	 *  Pattern.UNICODE_CASE
	 *  Pattern.UNIX_LINES
	 * @return 匹配到的列表 or null
	 * 	0:整个模式匹配到的结果
	 * 	1-n:子模式匹配到的结果
	 */
	public static String[] match(String regExp, String input, int flag) {
		boolean validateRegExp = !"".equals(regExp) && null != regExp;
		boolean validateInput  = !"".equals(input) && null != input;
		if ( !validateRegExp || !validateInput ) {
			return null;
		}
		
		Pattern pattern = Pattern.compile(regExp, flag);
		Matcher matcher = pattern.matcher(input);
		return RegExpUtil.matchResult(matcher);
	}
	
	/**
	 * 匹配一次
	 * @author 董光明
	 * @date 2016年5月6日
	 * @param regExp
	 * @param input
	 * @return 匹配到的列表 or null
	 * 	0:整个模式匹配到的结果
	 * 	1-n:子模式匹配到的结果
	 */
	public static String[] match(String regExp, String input) {
		boolean validateRegExp = !"".equals(regExp) && null != regExp;
		boolean validateInput  = !"".equals(input) && null != input;
		if ( !validateRegExp || !validateInput ) {
			return null;
		}
		
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(input);
		return RegExpUtil.matchResult(matcher);
	}
	
	/**
	 * 获取匹配结果
	 * @author 董光明
	 * @date 2016年5月6日
	 * @param matcher Matcher
	 * @return 返回匹配结果列表或者是null
	 */
	private static String[] matchResult(Matcher matcher) {
		if ( null == matcher ) {
			return null;
		}
		
		if ( !matcher.find() ) {
			return null;
		}
		
		int groupCount = matcher.groupCount();
		int arrayLength = groupCount + 1;
		
		String[] match = new String[arrayLength];		
		int i;
		for (i=0; i<arrayLength; i++) {
			match[i] = matcher.group(i);
			
		}
		
		return match;
	}
	
	/**
	 * 匹配所有符合条件的
	 * @author 董光明
	 * @date 2016年5月5日
	 * @param regex 正则表达式
	 * @param input 原字符串
	 * @param flag 匹配模式
	 * @return
	 */
	public static ArrayList<ArrayList<String>> matchAll(String regExp, String input, int flag) {
		boolean validateRegExp = !"".equals(regExp) && null != regExp;
		boolean validateInput  = !"".equals(input) && null != input;
		if ( !validateRegExp || !validateInput ) {
			return null;
		}
		
		Pattern pattern = Pattern.compile(regExp, flag);
		Matcher matcher = pattern.matcher(input);
		
		return RegExpUtil.matchResultAll(matcher);
	}
	
	/**
	 * 匹配所有符合条件的
	 * @author 董光明
	 * @date 2016年5月6日
	 * @param regExp 正则表达式
	 * @param input 原字符串
	 * @return 二维ArrayList，每个元素包含整个模式匹配到的结果和所有子模式匹配到的结果
	 * 元素0: 整个模式匹配结果
	 * 1-n: 子模式匹配到的结果
	 */
	public static ArrayList<ArrayList<String>> matchAll(String regExp, String input) {
		boolean validateRegExp = !"".equals(regExp) && null != regExp;
		boolean validateInput  = !"".equals(input) && null != input;
		if ( !validateRegExp || !validateInput ) {
			return null;
		}
		
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(input);
		
		return RegExpUtil.matchResultAll(matcher);
	}
	
	/**
	 * 获取所有的匹配结果
	 * @author 董光明
	 * @date 2016年5月6日
	 * @param matcher
	 * @return
	 */
	private static ArrayList<ArrayList<String>> matchResultAll(Matcher matcher) {
		if ( null == matcher ) {
			return null;
		}
		
		int groupLength = matcher.groupCount();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		while (matcher.find()) {
			ArrayList<String> subPattern = new ArrayList<String>();
			int i;
			for (i=0; i<=groupLength; i++) {
				subPattern.add(matcher.group(i));
			}
			
			result.add(subPattern);
		}
		
		
		return result;
	}
}
