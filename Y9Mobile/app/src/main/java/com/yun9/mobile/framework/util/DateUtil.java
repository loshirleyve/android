package com.yun9.mobile.framework.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @描述 日期转换、计算工具类(可以参考使用DateUtils的方法)
 *
 * 
 */
public class DateUtil {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DateUtil.class);
	
	public static final char[] DAY_OF_WEEK = new char[]{'日','一','二','三','四','五','六'};

	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 将传入时间初始化为当天的最初时间（即00时00分00秒）
	 * 
	 * @param date
	 *            时间
	 * @return 当天最初时间
	 */
	public static Date setAsBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getDate(long date, int n) {
		if (!AssertValue.isNotNull(date)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateFormatUtil.parse(date));
		cal.add(Calendar.HOUR, n);
		return cal.getTime();
	}

	public static String getDateStr(long date) {
		return DateFormatUtil.format(getDate(date, 0),
				StringPool.DATE_FORMAT_DATETIME);
	}

	public static String getDateStr(long date, int n) {
		return DateFormatUtil.format(getDate(date, n),
				StringPool.DATE_FORMAT_DATETIME);
	}

	public static Date calculateDate(Date date, int n, int type) {
		if (!AssertValue.isNotNull(date)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, n);
		return cal.getTime();
	}

	/**
	 * 将传入时间初始化为当天的结束时间（即23时59分59秒）
	 * 
	 * @param date
	 *            时间
	 * @return 当天结束时间
	 */
	public static Date modifyDate(Date date, int day, int hour, int minute,
			int second) {
		if (!AssertValue.isNotNull(date)) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int d = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, d + day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 将传入时间初始化为当天的结束时间（即23时59分59秒）
	 * 
	 * @param date
	 *            时间
	 * @return 当天结束时间
	 */
	public static Date setAsEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 取当前系统日期，并按指定格式或者是默认格式返回
	 * 
	 * @param style
	 *            指定格式
	 * @return 当前系统日期（字符串）
	 */
	public static String getCurrentTime(String style) {
		if (!AssertValue.isNotNullAndNotEmpty(style))
			style = StringPool.DATE_FORMAT_DATETIME;
		return DateFormatUtil.format(new Date(), style);
	}

	/**
	 * 取当前系统日期，并按指定格式（yyyy-MM-dd HH:mm:ss ）
	 * 
	 * @return 当前系统日期
	 */
	public static String getCurrentTime() {
		return getCurrentTime("");
	}

	/**
	 * 取当前系统日期（日期格式）
	 * 
	 * @return 当前系统日期（日期）
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 取当前系统日期
	 * 
	 * @return 当前系统日期（Long格式）
	 */
	public static long getCurrentTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * 取得指定年月的天数
	 * 
	 * @param year
	 *            实际年份
	 * @param mon
	 *            实际月份 数值范围是1~12
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 取得某月第一天为星期几。<br>
	 * 星期天为1。 星期六为7。
	 * 
	 * @param year
	 * @param mon
	 * @return
	 */
	public static int getWeekDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 比较两个时间大小，结束时间是否大于开始时间 传入的是字符串，会转成日期对象在进行比较。
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static boolean compare(String beginDateStr, String endDateStr) {
		try {
			Date beginDate = DateFormatUtil.parse(beginDateStr);
			Date endDate = DateFormatUtil.parse(endDateStr);
			return beginDate.compareTo(endDate) < 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 比较两个时间大小，结束时间是否大于开始时间 传入的是字符串，会转成日期对象在进行比较。
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static int compareTo(String beginDateStr, String endDateStr) {
		try {
			Date beginDate = DateFormatUtil.parse(beginDateStr);
			Date endDate = DateFormatUtil.parse(endDateStr);
			return beginDate.compareTo(endDate);
		} catch (Exception e) {
			return -2;
		}
	}

	/**
	 * 取得日期。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return
	 */
	public static Date getDate(int year, int month, int date) {
		return getDate(year, month, date, 0, 0, 0);
	}

	/**
	 * 取得日期。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param date
	 *            日
	 * @param hourOfDay
	 *            小时
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date, hourOfDay, minute, second);
		return cal.getTime();
	}

	/**
	 * 获取开始和结束时间的毫秒差
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static long getTime(Date startTime, Date endTime) {
		return endTime.getTime() - startTime.getTime();
	}

	/**
	 * 获取指定时间到系统时间的持续时间
	 * 
	 * @param date
	 *            指定时间
	 * @return
	 */
	public static String getDurationTime(Date date) {
		return getDurationTime(date, new Date());
	}

	/**
	 * 获取持续时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getDurationTime(Date startTime, Date endTime) {
		if (startTime == null || endTime == null)
			return "";
		Long millseconds = getTime(startTime, new Date());
		return getTime(millseconds);
	}
	
	/**
	 * 
	 * @param month 1~12
	 * @return
	 */
	public static Date getBeginOfMonth(int month) {
		Date now = getCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param month 0~11
	 * @return
	 */
	public static Date getEndOfMonth(int month) {
		Date now = getCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 获取今天是星期几，返回'日','一','二','三','四','五','六'
	 * @return
	 */
	public static char getDayOfWeek() {
		Date now = getCurrentDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		return DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) -1];
	}

	/**
	 * 根据长整形的毫秒数返回字符串类型的时间段
	 * 
	 * @param millseconds
	 *            毫秒数
	 * @return
	 */
	public static String getTime(Long millseconds) {
		StringBuffer time = new StringBuffer();
		if (millseconds == null)
			return "";
		int days = (int) (long) millseconds / 1000 / 60 / 60 / 24;
		if (days > 0)
			time.append(days).append("天");
		long hourMillseconds = millseconds - days * 1000 * 60 * 60 * 24;
		int hours = (int) hourMillseconds / 1000 / 60 / 60;
		if (hours > 0)
			time.append(hours).append("小时");
		long minuteMillseconds = millseconds - days * 1000 * 60 * 60 * 24
				- hours * 1000 * 60 * 60;
		int minutes = (int) minuteMillseconds / 1000 / 60;
		if (minutes > 0)
			time.append(minutes).append("分钟");
		return time.toString();
	}
	
	
	public static Date getStartDay(int day) {
	    Calendar c1 = new GregorianCalendar();
	    c1.set(Calendar.HOUR_OF_DAY, 0);
	    c1.set(Calendar.MINUTE, 0);
	    c1.set(Calendar.SECOND, 0);
	    c1.set(Calendar.DAY_OF_MONTH,day);
	    return c1.getTime();
	}
	
	public static Date getEndDay(int day) {
	    Calendar c2 = new GregorianCalendar();
	    c2.set(Calendar.HOUR_OF_DAY, 23);
	    c2.set(Calendar.MINUTE, 59);
	    c2.set(Calendar.SECOND, 59);
	    c2.set(Calendar.DAY_OF_MONTH,day);
	    return c2.getTime();
	}
	
	
	/**
	 * 获取传入的参数（天）是星期几，返回'日','一','二','三','四','五','六'
	 * @return
	 */
	public static char getDayOfWeek(int day) {
		Date now = getStartDay(day);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		return DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK) -1];
	}
	
	/**
	 *查询传入当月的某一个的时间是当月的第几天 
	 */
	public static int getDayByDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
}
