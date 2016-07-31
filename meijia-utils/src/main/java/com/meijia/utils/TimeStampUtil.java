package com.meijia.utils;

import java.text.ParseException;
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
	 * 
	 * @return long
	 */
	public static Long getNow() {
		return getMillisOfDate(DateUtil.getNowOfDate());
	}

	/**
	 * 当前时间戳, 注意为精确到秒
	 * 
	 * @return long
	 */
	public static Long getNowSecond() {
		return getMillisOfDate(DateUtil.getNowOfDate()) / 1000;
	}

	/*
	 * 当前 时间戳, 精确到秒。
	 * 
	 * 但 日期 精确到分钟, 针对 精确到 分钟的 定时任务
	 */
	public static Long getNowSecondByMinute() throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = simpleDateFormat.parse(DateUtil.getNow("yyyy-MM-dd HH:mm"));
		Long timeStemp = date.getTime() / 1000;
		return timeStemp;
	}

	/**
	 * 返回日期字符串的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillisOfDay(String strDate) {
		Date date = DateUtil.parse(strDate);
		return getMillisOfDate(date);
	}

	/**
	 * 返回日期字符串的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillisOfDayFull(String strDate) {
		Date date = DateUtil.parseFull(strDate);
		return getMillisOfDate(date);
	}

	/**
	 * 返回日期对象的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillisOfDate(Date date) {
		DEFAULT_CALENDAR.setTime(date);
		return DEFAULT_CALENDAR.getTimeInMillis();
	}

	/**
	 * 返回两个时间戳相差毫秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long compareTimeStr(Long startTime, Long endTime) {
		return endTime - startTime;
	}

	/**
	 * 今天开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getBeginOfToday() {
		String today = DateUtil.getBeginOfDay();
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}

	/**
	 * 结束开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getEndOfToday() {
		String today = DateUtil.getEndOfDay();
		return getMillisOfDayFull(today) / 1000;
	}

	/**
	 * 本周周一开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getBeginOfWeek() {
		String today = DateUtil.getFirstDayOfWeek();
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}

	/**
	 * 昨天开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getBeginOfYesterDay() {
		String y = DateUtil.getYesterday();
		Date d = DateUtil.parse(y);
		return getMillisOfDate(d) / 1000;
	}
	
	/**
	 * 结束开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getEndOfYesterDay() {
		String y = DateUtil.getYesterday();
		y = y + " 23:59:59";
		return getMillisOfDayFull(y) / 1000;
	}

	/**
	 * 本周周一开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getBeginOfMonth(int year, int month) {
		String today = DateUtil.getFirstDayOfMonth(year, month);
		Date d = DateUtil.parse(today);
		return getMillisOfDate(d) / 1000;
	}
	
	/**
	 * 本周周一开始时间戳，注意为精确到毫秒
	 * 
	 * @return long
	 */
	public static Long getEndOfMonth(int year, int month) {
		String today = DateUtil.getLastDayOfMonth(year, month);
		Date d = DateUtil.parseFull(today + " 23:59:59");
		return getMillisOfDate(d) / 1000;
	}

	/**
	 * 根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static String timeStampToDateStr(Long t) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FULL_PATTERN);
		String str = df.format(t);
		return str;
	}

	/**
	 * 根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static String timeStampToDateStr(Long t, String patten) {
		SimpleDateFormat df = new SimpleDateFormat(patten);
		String str = df.format(t);
		return str;
	}

	/**
	 * 根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static Date timeStampToDate(Long t) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FULL_PATTERN);
		String str = df.format(t);
		return DateUtil.parse(str);
	}

	public static Date timeStampToDateFull(Long t, String format) {
		if (format == null)
			format = DEFAULT_FULL_PATTERN;
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

	/**
	 * 如果当天则返回 HH:mm, 否则返回 yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */

	public static String fromTodayStr(Long time) {

		Long todayBegin = TimeStampUtil.getBeginOfToday() * 1000;

		if (time >= todayBegin) {
			return TimeStampUtil.timeStampToDateStr(time, "HH:mm");
		} else {
			return TimeStampUtil.timeStampToDateStr(time, "yyyy-MM-dd HH:mm");
		}
	}

	/**
	 * 根据时间戳 -> String yyyy-MM-dd HH:MM:ss
	 */
	public static String timeStampToChineseDateStr(Long t, String patten) {
		SimpleDateFormat df = new SimpleDateFormat(patten);
		String str = df.format(t);
		return str;
	}

	public static int getHour(Long time) {
		String hourStr = TimeStampUtil.timeStampToDateStr(time, "HH");
		return Integer.valueOf(hourStr);
	}

	public static void main(String[] args) {
		// Long serviceTime = (long) 1458294331;

		// System.out.println(DateUtil.format(serviceTime * 1000,
		// TimeStampUtil.DEFAULT_FULL_PATTERN));
		//
		// System.out.println(TimeStampUtil.timeStampToDateHour(serviceTime*1000));
		// System.out.println(TimeStampUtil.timeStampToDateStr(serviceTime*1000,
		// TimeStampUtil.DEFAULT_FULL_PATTERN));
		//
		// Long remindTime = serviceTime - 5 * 60;
		// System.out.println(TimeStampUtil.timeStampToDateStr(remindTime*1000,
		// TimeStampUtil.DEFAULT_FULL_PATTERN));
		// System.out.println(TimeStampUtil.getBeginOfYesterDay());

		// System.out.println(TimeStampUtil.getEndOfToday());

		Long nowMin = 1458295140000L;
		Long serviceTime = 1458295440L;

		System.out.println((long) 180 * 3600 * 24 * 1000);

		System.out.println(timeStampToChineseDateStr(serviceTime, "yyyy年MM月dd日"));

		// serviceTime = TimeStampUtil.timeStampToDateHour(serviceTime * 1000);
		// int remindMin = 5;
		// Long remindTime = serviceTime - remindMin * 60;
		// System.out.println(TimeStampUtil.timeStampToDateStr(nowMin,DateUtil.DEFAULT_FULL_PATTERN)
		// + "----" + TimeStampUtil.timeStampToDateStr(remindTime *
		// 1000,DateUtil.DEFAULT_FULL_PATTERN));
		
		System.out.println(TimeStampUtil.getHour(1469786410L * 1000));
		
		System.out.println(TimeStampUtil.getBeginOfYesterDay());
		System.out.println(TimeStampUtil.getEndOfYesterDay());
		
		System.out.println(TimeStampUtil.getBeginOfMonth(2016, 2));
		System.out.println(TimeStampUtil.getEndOfMonth(2016, 2));
	}
}