package com.meijia.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * String常用工具类.. 持续更新ing..
 * 
 * @author dylan
 *
 */
public class StringUtil {

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
	 * 截取字符串，字母、汉字都可以，汉字不会截取半
	 * 
	 * @param str
	 *            字符串
	 * @param n
	 *            截取的长度，字母数，如果为汉字，一个汉字等于两个字母数
	 * @return
	 */
	public static String subStringByByte(String str, int n) {
		int num = 0;
		try {
			byte[] buf = str.getBytes("GBK");
			if (n >= buf.length) {
				return str;
			}
			boolean bChineseFirstHalf = false;
			for (int i = 0; i < n; i++) {
				if (buf[i] < 0 && !bChineseFirstHalf) {
					bChineseFirstHalf = true;
				} else {
					num++;
					bChineseFirstHalf = false;
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str.substring(0, num);
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
	
	
	//生成八位唯一编码
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
}
