package com.meijia.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * String常用工具类.. 持续更新ing..
 * 
 * @author dylan
 *
 */
public class StringUtil {

	static String[] bigNum = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	/**
	 * 判断字符串是否为空 or is NULL
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || str.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return str == null;
	}

	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 格式化字符串 如果为空，返回“”
	 * 
	 * @param str
	 * @return
	 */
	public static String formatString(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * MD5字符串加密
	 *
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public final static String md5(String str) throws NoSuchAlgorithmException {
		final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] btInput = str.getBytes();
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest md5Inst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		md5Inst.update(btInput);
		// 获得密文
		byte[] bytes = md5Inst.digest();

		StringBuffer strResult = new StringBuffer();
		// 把密文转换成十六进制的字符串形式
		for (int i = 0; i < bytes.length; i++) {
			strResult.append(hexDigits[(bytes[i] >> 4) & 0x0f]);
			strResult.append(hexDigits[bytes[i] & 0x0f]);
		}
		return strResult.toString();
	}

	/**
	 * SHA-1字符串加密
	 *
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public final static String sha1(String str) throws NoSuchAlgorithmException {
		final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] btInput = str.getBytes();
		// 获得SHA-1摘要算法的 MessageDigest 对象
		MessageDigest sha1Inst = MessageDigest.getInstance("SHA-1");
		// 使用指定的字节更新摘要
		sha1Inst.update(btInput);
		// 获得密文
		byte[] bytes = sha1Inst.digest();

		StringBuffer strResult = new StringBuffer();
		// 把密文转换成十六进制的字符串形式
		for (int i = 0; i < bytes.length; i++) {
			strResult.append(hexDigits[(bytes[i] >> 4) & 0x0f]);
			strResult.append(hexDigits[bytes[i] & 0x0f]);
		}
		return strResult.toString();
	}

	public static String[] clean(final String[] v) {
		List<String> list = new ArrayList<String>(Arrays.asList(v));
		list.removeAll(Collections.singleton(""));
		return list.toArray(new String[list.size()]);
	}

	// 逗号分割
	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	// 下划线分割
	public static String[] convertStrToArrayByUL(String str) {
		String[] strArray = null;
		strArray = str.split("_"); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	/*
	 * 身份证号中间 用 * 显示
	 */
	public static String replaceCardIdByStar(String cardId) {
		// 去除空格，数据库中记录有空格
		String cardNo = cardId.replaceAll(" ", "");

		String star = cardNo.substring(5, 15);
		String replaceAll = cardNo.replaceAll(star, "**********");

		return replaceAll;
	}

	public static String getZhNum(String str) {
		int t = Integer.parseInt(str);
		return bigNum[t];
	}

	// 生成八位唯一编码
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	public static String setDoubleQuote(String myText) {
		String quoteText = "";
		// if (!myText.isEmpty()) {
		quoteText = "\"" + myText + "\"";
		// }
		return quoteText;
	}

	public static boolean isContainChinese(String str) {

		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		String a = "董光明a是b一个编c外人员";
		System.out.println((a.substring(0, 5)));
		System.out.println((a.substring(a.length() - 4, a.length())));

		String b = "mcccb";
		System.out.println(b.substring(0, 2));
		System.out.println(b.substring(b.length() - 2, b.length()));
	}

}
