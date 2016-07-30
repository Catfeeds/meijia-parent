package com.meijia.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {

	public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	public static final String DEFAULT_TIME = "HH:mm:ss";

	public static final String DEFAULT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final Calendar DEFAULT_CALENDAR = Calendar.getInstance();

	public static final long ONE_MINUTE = 60;
	public static final long ONE_HOUR = 3600;
	public static final long ONE_DAY = 86400;
	public static final long ONE_MONTH = 2592000;
	public static final long ONE_YEAR = 31104000;	

	private static final int[] quarters = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};

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
	 * 获得某年某月某日时间戳
	 * @return yyyy-MM-dd
	 */
	public static String  getSomeDayOfMonth(int year,int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.  set(Calendar.DATE, day);
		String  lastDay =format(cal.getTime(), DEFAULT_PATTERN);
		//Long lastDayLong = TimeStampUtil.getMillisOfDay(format(cal.getTime(), DEFAULT_PATTERN));
		return lastDay;
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
	
    /**
     * 距离今天多久
     * @param date
     *     1）几分钟前： 创建时间 <=60分钟。
	 *	   2）几小时前：60分钟 < 创建时间 <= 24小时
	 *	   3）几天前：1天 < 创建时间 <= 7天
	 *	   4）2015.8.18：7天<创建时间<= 无限
     * @return
     *
     */
    public static String fromToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
 
        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago <= ONE_HOUR)
            return (ago / ONE_MINUTE) + "分钟前";
        else if ( ago > ONE_HOUR && ago <= ONE_DAY)
            return (ago/ONE_HOUR) +"小时前";
        else if (ago > ONE_DAY && ago <= ONE_DAY * 7)
        	return (ago/ONE_DAY) +"天前";
        else if (ago > ONE_DAY * 7) {
        	return DateUtil.format(date, "yyyy.MM.dd");
        } else {
        	return DateUtil.format(date, "yyyy.MM.dd");
        }
 
    }	
    
    /**
	 * 某一天的结束
	 * @param  day:yyyy-MM-dd
	 * @return
	 */
	public static String getBeginOfYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);		
		return format(c.getTime(), "yyyy-MM-dd");
	}	
	
	public static String getLastOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year -1 );
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH,31);		
		return format(c.getTime(), "yyyy-MM-dd");
	}		
	
	/**
	 * 当前年份
	 * @return
	 */
	public static int getYear(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);	
		return c.get(Calendar.YEAR);
	}	

	/**
	 * 当前月份
	 * @return
	 */
	public static int getMonth(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);		
		return c.get(Calendar.MONTH) + 1;
	}	
	
	/**
	 * 得到两个日期相差的天数  
	 * @param date1 <String>
	 * @param date2 <String>
	 * @return int
	 * @throws ParseException
	*/
   public static int getDateSpace(String date1, String date2) {

       Calendar calst = Calendar.getInstance();;
       Calendar caled = Calendar.getInstance();

       calst.setTime(DateUtil.parse(date1));
       caled.setTime(DateUtil.parse(date2));

        //设置时间为0时   
        calst.set(Calendar.HOUR_OF_DAY, 0);   
        calst.set(Calendar.MINUTE, 0);   
        calst.set(Calendar.SECOND, 0);   
        caled.set(Calendar.HOUR_OF_DAY, 0);   
        caled.set(Calendar.MINUTE, 0);   
        caled.set(Calendar.SECOND, 0);   
       //得到两个日期相差的天数   
        int days = ((int)(caled.getTime().getTime()/1000)-(int)(calst.getTime().getTime()/1000))/3600/24;   
        
       return days;   
   }

	

	/**
	 * 根据当前月份获得季度
	 * 		
	 * @param month
	 * 
	 * 		12月 会越界，因而是 month-1
	 */
	
	public static int getQuarter(int month) {
		return quarters[month-1];
	}			
	
	
	/*
	 * 根据 时间戳 得到  日期时间 （UTC 格式）-->  Thu May 19 14:19:54 CST 2016
		
		运营平台--  <fmt:formatDate> 日期格式化标签  ,需要 date类型时间,用来 展示
		
	 */
	public static Date timeStampToDate(Long time) throws ParseException{
			
		  //如果 时间 戳小于  12位 数字, 表示为 秒值，转换为 毫秒值	
		  if(time < 100000000000L){
			  time *= 1000;
		  }	
		
		  //时间戳转化为Sting或Date
	      SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String d = format.format(time);
	      Date date= format.parse(d);
	      
//	      System.out.println(date);
	      return date;
	}
	
	public static List<String> getAllDaysOfMonth(int year, int month) {
		List<String> days = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month - 1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	    System.out.print(df.format(cal.getTime()));
	    for (int i = 0; i < maxDay; i++) {
	        cal.set(Calendar.DAY_OF_MONTH, i + 1);
//	        System.out.print(", " + df.format(cal.getTime()));
	        days.add(df.format(cal.getTime()));
	    }
	    
	    return days;
	}
	
	public static List<String> getAllWorkDaysOfMonth(int year, int month) {
		List<String> days = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//	    System.out.print(df.format(cal.getTime()));
	    for (int i = 1; i < maxDay; i++) {
	        cal.set(Calendar.DAY_OF_MONTH, i + 1);
//	        System.out.print(", " + df.format(cal.getTime()));
	        int day = cal.get(Calendar.DAY_OF_WEEK);    
	        if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){    
	        	days.add(df.format(cal.getTime()));
	        }
	    }
	    
	    return days;
	}
	
	/*
	 * 根据 时间戳 得到  字符串类型的 日期 格式 时间--> "2016-5-19 14:44:21"
	 */
	public static String timeStampToDateStr(Long time){
			
		  //如果 时间 戳小于  12位 数字, 表示为 秒值，转换为 毫秒值	
		  if(time < 100000000000L){
			  time *= 1000;
		  }
		
		  //时间戳转化为Sting 格式的日期字符串
	      SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String d = format.format(time);
	      
//	      System.out.println("Format To String(Date):"+d);
	      
	      return d;
	}
	
	
	
	
	public static void main(String[] args) throws ParseException {
//		List<String> result = DateUtil.getLastMonth(6, 12);
//		
//		System.out.println(result);
		
//		Date testDate = TimeStampUtil.timeStampToDateFull(1438159994000L , DEFAULT_FULL_PATTERN);
//		System.out.println(DateUtil.fromToday(testDate));
//		
//		String dateStr = "11-五月-2015";
//		System.out.println(DateUtil.isDate(dateStr));
		
//		System.out.println(DateUtil.getDateSpace("2016-01-27", "2016-01-26"));
//		timeStampToDate(0L);
		
//		System.out.println(java.sql.Date.valueOf("2016-6-8"));
		System.out.println(DateUtil.getAllDaysOfMonth(2016, 1));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 2));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 3));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 4));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 5));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 6));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 7));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 8));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 9));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 10));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 11));
//		System.out.println(DateUtil.getAllDaysOfMonth(2016, 12));
		
		System.out.println(DateUtil.getYear());
		
		int day = Integer.valueOf("2016-01-02".substring(8));
		System.out.println(String.valueOf(day));
		
		String w = "星期三";
		System.out.println(w.substring(2));
		
	}
}