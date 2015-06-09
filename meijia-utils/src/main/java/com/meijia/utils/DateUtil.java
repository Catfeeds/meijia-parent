package com.meijia.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.zxing.client.result.ResultParser;

public class DateUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	public static final String DEFAULT_TIME = "HH:mm:ss";

	public static final String DEFAULT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final Calendar DEFAULT_CALENDAR = Calendar.getInstance();


	/**
	 * 返回固定格式的日期
	 */
	public static String getDefaultDate(Object date){
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.format(date);
	}
	/**
	 * 返回固定格式的时间
	 */
	public static String getDefaultTime(Object Time){
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME);
		return sdf.format(Time);
	}
	/**
	 * 判断字符串是否为日期
	 * @param day:yyyy-MM-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		return isDate(date, DEFAULT_PATTERN);
	}

	/**
	 * 判断是否为日期
	 * @param date
	 * @param pattern
	 * @return boolean
	 */
	public static boolean isDate(String date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		return DateUtil.format(new Date(), DEFAULT_FULL_PATTERN);
	}

	/**
	 * 当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowOfDate() {
		return new Date();
	}

	/**
	 * 当前时间
	 * @return pattern格式
	 */
	public static String getNow(String pattern) {
		return DateUtil.format(new Date(), pattern);
	}

	/**
	 * 当前日期
	 * @return yyyy-MM-dd
	 */
	public static String getToday() {
		return DateUtil.format(new Date(), DEFAULT_PATTERN);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date) {
		DateFormat df = new SimpleDateFormat(DEFAULT_PATTERN);
		return df.format(date);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateFull(Date date) {
		DateFormat df = new SimpleDateFormat(DEFAULT_FULL_PATTERN);
		return df.format(date);
	}

	/**
	 * 字符串转换为日期对象
	 * String --> Date
	 * @param strDate
	 * @param dateFormat
	 * @return
	 */
	public static Date parse(String strDate, String dateFormat) {
		if (strDate == null || strDate.length() == 0) {
			return null;
		}
		Date date = null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 默认格式:yyyy-MM-dd
	 * @param strDate
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parse(String strDate) {
		return parse(strDate, DEFAULT_PATTERN);
	}

	/**
	 * 格式:yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 * @return
	 */
	public static Date parseFull(String strDate) {
		return parse(strDate, DEFAULT_FULL_PATTERN);
	}

	/**
	 * 比较日期大小
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 */
	public static boolean compare(String strStartDate, String strEndDate) {
		Date startDate = parse(strStartDate, DEFAULT_PATTERN);
		Date endDate = parse(strEndDate, DEFAULT_PATTERN);
		long startTime = TimeStampUtil.getMillisOfDate(startDate);
		long endTime = TimeStampUtil.getMillisOfDate(endDate);
		return endTime - startTime > 0;
	}

	/**
	 * 返回两个日期相差毫秒
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 */
	public static long compareDateStr(String strStartDate, String strEndDate) {
		Date startDate = parse(strStartDate, DEFAULT_PATTERN);
		Date endDate = parse(strEndDate, DEFAULT_PATTERN);
		long startTime = TimeStampUtil.getMillisOfDate(startDate);
		long endTime = TimeStampUtil.getMillisOfDate(endDate);
		return endTime - startTime;
	}
	/**
	 * 返回两个时间相差毫秒
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 */
	public static long compareTimeStr(String strStartDate, String strEndDate) {
		Date startDate = parse(strStartDate, DEFAULT_FULL_PATTERN);
		Date endDate = parse(strEndDate, DEFAULT_FULL_PATTERN);
		long startTime = TimeStampUtil.getMillisOfDate(startDate)/1000;
		long endTime = TimeStampUtil.getMillisOfDate(endDate)/1000;
		return endTime - startTime;
	}

	public static String addDay(Date date, int count, int field, String format) {
		DEFAULT_CALENDAR.setTime(date);
		int year = getYear();
		int month = getMonth() - 1;
		int day = getDay();
		int hours = getHours();
		int minutes = getMinutes();
		int seconds = getSeconds();
		Calendar calendar = new GregorianCalendar(year, month, day, hours, minutes, seconds);
		calendar.add(field, count);
		String tmpDate = format(calendar.getTime(), format);
		DEFAULT_CALENDAR.setTime(new Date());
		return tmpDate;
	}

	/**
	 * 得到本月最后一天
	 * @return yyyy-MM-dd
	 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getYear());
		cal.set(Calendar.MONTH, getMonth());
		cal.set(Calendar.DATE, 0);
		return format(cal.getTime(), DEFAULT_PATTERN);
	}
	/**
	 * 获得某年某月的最后一天时间戳
	 * @return yyyy-MM-dd
	 */
	public static String  getLastDayOfMonth(int year,int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.  set(Calendar.DATE, 0);
		String  lastDay =format(cal.getTime(), DEFAULT_PATTERN);
		//Long lastDayLong = TimeStampUtil.getMillisOfDay(format(cal.getTime(), DEFAULT_PATTERN));
		return lastDay;
	}

	/**
	 * 获得某年某月的第一天时间戳
	 * @return yyyy-MM-dd
	 */
	public static String getFirstDayOfMonth(int year,int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, (month-1));
		cal.set(Calendar.DAY_OF_MONTH,1);
		//cal.set(Calendar.DATE, 1);
		String firstDay = format(cal.getTime(), DEFAULT_PATTERN);
		//Long firstDayLong = TimeStampUtil.getMillisOfDay(format(cal.getTime(), DEFAULT_PATTERN));
		return firstDay;
	}

	/**
	 * 本周第一天
	 * @return yyyy-MM-dd
	 */
	public static String getFirstDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return format(c.getTime(), DEFAULT_PATTERN);
	}

	/**
	 * 昨天的日期字符串
	 * @return yyyy-MM-dd HH:mm:ss
	 */

	public static String getYesterday() {
		return addDay(new Date(), -1, Calendar.DATE, DEFAULT_PATTERN);
	}

	/**
	 *  今天的开始时间
	 */
	public static String getBeginOfDay() {
		return getBeginOfDay(getToday());
	}

	/**
	 *  今天的结束时间
	 */
	public static String getEndOfDay() {
		return getEndOfDay(getToday());
	}

	/**
	 * 某一天的开始
	 * @param day:yyyy-MM-dd
	 * @return
	 */
	public static String getBeginOfDay(String day) {
		if (StringUtil.isEmpty(day) || !isDate(day)) {
			return null;
		}
		return day + " 00:00:00";
	}

	/**
	 * 某一天的结束
	 * @param  day:yyyy-MM-dd
	 * @return
	 */
	public static String getEndOfDay(String day) {
		if (StringUtil.isEmpty(day) || !isDate(day)) {
			return null;
		}
		return day + " 23:59:59";
	}



	/**
	 * 当前年份
	 * @return
	 */
	public static int getYear() {
		return DEFAULT_CALENDAR.get(Calendar.YEAR);
	}

	/**
	 * 当前月份
	 * @return
	 */
	public static int getMonth() {
		return DEFAULT_CALENDAR.get(Calendar.MONTH) + 1;
	}

	/**
	 * 当天
	 * @return
	 */
	public static int getDay() {
		return DEFAULT_CALENDAR.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 当前小时
	 * @return
	 */
	public static int getHours() {
		return DEFAULT_CALENDAR.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 当前分钟
	 * @return
	 */
	public static int getMinutes() {
		return DEFAULT_CALENDAR.get(Calendar.MINUTE);
	}

	  /**
     * 获取日期的星期。失败返回null。
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
        case 0:
            week = Week.SUNDAY;
            break;
        case 1:
            week = Week.MONDAY;
            break;
        case 2:
            week = Week.TUESDAY;
            break;
        case 3:
            week = Week.WEDNESDAY;
            break;
        case 4:
            week = Week.THURSDAY;
            break;
        case 5:
            week = Week.FRIDAY;
            break;
        case 6:
            week = Week.SATURDAY;
            break;
        }
        return week;
    }

	/**
	 * 当前秒
	 * @return
	 */
	public static int getSeconds() {
		return DEFAULT_CALENDAR.get(Calendar.SECOND);
	}

	public static String getDefaultPattern() {
		return DEFAULT_PATTERN;
	}

	public static String getDefaultFullPattern() {
		return DEFAULT_FULL_PATTERN;
	}

	public static Calendar getDefaultCalendar() {
		return DEFAULT_CALENDAR;
	}
	
	/**
	 *  根据月份获得过去几个月的数组 
	 */
	public static List<String> getLastMonth(int curMonth, int lastCount) {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.MONTH, curMonth - 1);
		cal.set(Calendar.DAY_OF_MONTH,1);		
		
		List<String> list = new ArrayList<String>();	
		String yyyyMM = format(cal.getTime(), "yyyy-MM");

		list.add(yyyyMM);
		for(int i=0;i < lastCount -1 ;i++){
			
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) - 1));
			yyyyMM = format(cal.getTime(), "yyyy-MM");
			list.add(yyyyMM);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		List<String> result = DateUtil.getLastMonth(6, 12);
		
		System.out.println(result);
	
	}
}