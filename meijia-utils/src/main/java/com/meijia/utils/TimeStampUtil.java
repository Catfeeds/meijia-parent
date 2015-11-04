package com.meijia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStampUtil {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
		
	private static final String DEFAULT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

	private static final Calendar DEFAULT_CALENDAR = Calendar.getInstance();

	/**
	 * 当前时间戳, 注意为精确到毫秒
	 * @return long
	 */
	public static Long getNow() {
		return getMillisOfDate(DateUtil.getNowOfDate());
	}
	
	/**
	 * 当前时间戳, 注意为精确到毫秒
	 * @return long
	 */
	public static Long getNowSecond() {
		return getMillisOfDate(DateUtil.getNowOfDate())/1000;
	}

	/**
	 * 返回日期字符串的毫秒数
	 * @param date
	 * @return
	 */
	public static long getMillisOfDay(String strDate) {
		Date date = DateUtil.parse(strDate);
		return getMillisOfDate(date);
	}

	/**
	 * 返回日期字符串的毫秒数
	 * @param date
	 * @return
	 */
	public static long getMillisOfDayFull(String strDate) {
		Date date = DateUtil.parseFull(strDate);
		return getMillisOfDate(date);
	}

	/**
	 * 返回日期对象的毫秒数
	 * @param date
	 * @return
	 */
	public static long getMillisOfDate(Date date) {
		DEFAULT_CALENDAR.setTime(date);
		return DEFAULT_CALENDAR.getTimeInMillis();
	}

	/**
	 * 返回两个时间戳相差毫秒
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long compareTimeStr(Long startTime, Long endTime) {
		return endTime - startTime;
	}

	/**
	 * 今天开始时间戳，注意为精确到毫秒
	 * @return long
	 */
	public static Long getBeginOfToday() {
		String today = DateUtil.getBeginOfDay();
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}

	/**
	 * 结束开始时间戳，注意为精确到毫秒
	 * @return long
	 */
	public static Long getEndOfToday() {
		String today = DateUtil.getEndOfDay();
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}
	
	/**
	 * 今天开始时间戳，注意为精确到毫秒
	 * @return long
	 */
	public static Long getBeginOfYesterDay() {
		String today = DateUtil.getYesterday();
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}	

	/**
	 *  根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static String timeStampToDateStr(Long t) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FULL_PATTERN);
		String str = df.format(t);
		return str;
	}

	/**
	 *  根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static String timeStampToDateStr(Long t, String patten) {
		SimpleDateFormat df = new SimpleDateFormat(patten);
		String str = df.format(t);
		return str;
	}

	/**
	 *  根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static Date timeStampToDate(Long t) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FULL_PATTERN);
		String str = df.format(t);
		return DateUtil.parse(str);
	}

	public static Date timeStampToDateFull(Long t, String format) {
		if (format == null) format = DEFAULT_FULL_PATTERN;
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(t);
		return DateUtil.parse(str, format);
	}
	
	public static Long timeStampToDateHour(Long t) {
		String format = "yyyy-MM-dd HH:mm:00";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(t);
		Date pDate = DateUtil.parse(str, format);
		return getMillisOfDate(pDate);
	}

	public static void main(String[] args) {
		Long t = (long) 1427328056;
		Date startTime = TimeStampUtil.timeStampToDate(t*1000);
		System.out.println(DateUtil.format(startTime, TimeStampUtil.DEFAULT_FULL_PATTERN));
		System.out.println(TimeStampUtil.timeStampToDateStr(t*1000, TimeStampUtil.DEFAULT_FULL_PATTERN));
		System.out.println(TimeStampUtil.timeStampToDateHour(t*1000));
		
		System.out.println(TimeStampUtil.getBeginOfYesterDay());
	}
}