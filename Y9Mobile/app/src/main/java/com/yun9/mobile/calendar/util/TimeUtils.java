package com.yun9.mobile.calendar.util;

import android.annotation.SuppressLint;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yun9.mobile.calendar.entity.DateInfo;

/**
 * 日历时间计算类
 * @author Kass
 *
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {
	/**
	 * 获取系统时间是哪一年
	 * @return
	 */
	public static int getCurrentYear() {
		Time t = new Time();
		t.setToNow();
		return t.year;
	}

	/**
	 * 获取系统时间是哪个月
	 * @return
	 */
	public static int getCurrentMonth() {
		Time t = new Time();
		t.setToNow();
		return t.month + 1;
	}

	/**
	 * 获取系统时间是哪一天
	 * @return
	 */
	public static int getCurrentDay() {
		Time t = new Time();
		t.setToNow();
		return t.monthDay;
	}
	
	/**
	 * 获取开始排班的日期或排班结束的日期
	 * @param year
	 * @param month
	 * @param type
	 * @return
	 */
	public static String getTimeByCurrentDate(int year, int month, String type) {
		String beginDate = getFormatFirstDay(year, month - 1);
		String endDate = getFormatFinalDay(year, month + 1);
		if (month == 1) {
			beginDate = getFormatFirstDay(year - 1, 12);
		}
		else if (month == 12) {
			endDate = getFormatFinalDay(year + 1, 1);
		}
		if (type.equals("begin")) {
    		return beginDate;
    	}
		else if(type.equals("end")) {
			return endDate;
		}
		return null;
	}

	/**
	 * 获得当前GridView的年或月
	 * @param position
	 * @param originYear
	 * @param originMonth
	 * @param type
	 * @return
	 */
	public static int getTimeByPosition(int position, int originYear, int originMonth, String type) {
    	int year = originYear, month = originMonth;
    	if (position > 500) {
    		for (int i = 500; i < position; i++) {
    			month++;
    			if (month == 13) {
    				month = 1;
    				year++;
    			}
    		}
    	} 
    	else if (position < 500) {
    		for (int i = 500; i > position; i--) {
    			month--;
    			if (month == 0) {
    				month = 12;
    				year--;
    			}
    		}
    	}
    	if (type.equals("year")) {
    		return year;
    	}
    	return month;
	}

	/**
	 * 判断是否为周末
	 * @param date
	 * @return
	 */
	public static int getWeekDay(String date) {  
        Calendar calendar = Calendar.getInstance();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        try {  
            calendar.setTime(sdf.parse(date));  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  
        if (dayOfWeek == 1) {
            dayOfWeek = 0;  
        }
        else {
            dayOfWeek -= 1; 
        }
        return dayOfWeek;  
    }  
	
	/**
	 * 判断是否为闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if (year % 400 == 0 || year % 100 != 0 && year % 4 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取这一年这个月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			if (isLeapYear(year)) {
				return 29;
			}
			return 28;
		}
	}
	
	/**
	 * 格式化这个月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFormatFirstDay(int year, int month) {
    	String formatYear = year + "";
    	String formatMonth = "";
    	if (month < 10) {
    		formatMonth = "0" + month;
    	} else {
    		formatMonth = month + "";
    	}
    	return formatYear + "-" + formatMonth + "-01";
	}
	
	/**
	 * 格式化这个月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFormatFinalDay(int year, int month) {
		String formatYear = year + "";
    	String formatMonth = "";
    	String day = String.valueOf(getDaysOfMonth(year, month));
    	if (month < 10) {
    		formatMonth = "0" + month;
    	} else {
    		formatMonth = month + "";
    	}
    	return formatYear + "-" + formatMonth + "-" + day;
	}
	
	/**
	 * 格式化这天的日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getFormatDate(int year, int month, int day) {
    	String formatYear = year + "";
    	String formatMonth = "";
    	String formatDay = "";
    	if (month < 10) {
    		formatMonth = "0" + month;
    	} else {
    		formatMonth = month + "";
    	}
    	if (day < 10) {
    		formatDay = "0" + day;
    	} else {
    		formatDay = day + "";
    	}
    	return formatYear + "-" + formatMonth + "-" + formatDay;
	}
	
	/**
	 * 时间戳转换成字符窜
	 * @param time
	 * @return
	 */
	public static String getDateToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }
	
	/**
	 * 将字符串转为时间戳
	 * @param time
	 * @return
	 */
	public static long getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/**
	 * 初始化日历
	 * @param formatDate
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static List<DateInfo> initCalendar(String formatDate, int month) throws Exception {
		int dates = 1;
		int year = Integer.parseInt(formatDate.substring(0, 4));
		int [] allDates = new int[42];
		for (int i = 0; i < allDates.length; i++) {
			allDates[i] = -1;
		}
		int firstDayOfMonth = TimeUtils.getWeekDay(formatDate);
		int totalDays = TimeUtils.getDaysOfMonth(year, month);
		for (int i = firstDayOfMonth; i < totalDays + firstDayOfMonth; i++) {
    		allDates[i] = dates;
    		dates++;
    	}
		
		List<DateInfo> list = new ArrayList<DateInfo>();
		DateInfo dateInfo;
		for (int i = 0; i < allDates.length; i++) {
    		dateInfo = new DateInfo();
    		dateInfo.setDate(allDates[i]);
    		if (allDates[i] == -1) {
    			dateInfo.setLunarDate("");
    			dateInfo.setThisMonth(false);
    			dateInfo.setWeekend(false);
    		}
    		else {
    			String date = TimeUtils.getFormatDate(year, month, allDates[i]);
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			long time = sdf.parse(date).getTime();
    			Lunar lunar = new Lunar(time);
    			if (lunar.isSFestival()) {
					dateInfo.setLunarDate(lunar.getSFestivalName());
					dateInfo.setHoliday(true);
				} else {
					if (lunar.isLFestival() && lunar.getLunarMonthString().substring(0, 1).equals("闰") == false) {
						dateInfo.setLunarDate(lunar.getLFestivalName());
						dateInfo.setHoliday(true);
					} else {
						if (lunar.getLunarDayString().equals("初一")) {
							dateInfo.setLunarDate(lunar.getLunarMonthString() + "月");
						} else {
							dateInfo.setLunarDate(lunar.getLunarDayString());
						}
						dateInfo.setHoliday(false);
					}
				}
    			dateInfo.setThisMonth(true);
    			int t = getWeekDay(getFormatDate(year, month, allDates[i]));
    			if (t == 0 || t == 6) {
    				dateInfo.setWeekend(true);
    			}
    			else {
    				dateInfo.setWeekend(false);
    			}
    		}
    		list.add(dateInfo);
    	}
    	
    	int front = DataUtils.getFirstIndexOf(list);
    	int back = DataUtils.getLastIndexOf(list);
    	int lastMonthDays;	// 上个月的天数
    	if (month == 1) {
    		lastMonthDays = getDaysOfMonth(year - 1, 12);
    	}
    	else {
    		lastMonthDays = getDaysOfMonth(year, month - 1); 
    	}
    	int nextMonthDays = 1;
    	for (int i = front - 1; i >= 0; i--) {
    		list.get(i).setDate(lastMonthDays);
    		lastMonthDays--;
    	}
    	for (int i = back + 1; i < list.size(); i++) {
    		list.get(i).setDate(nextMonthDays);
    		nextMonthDays++;
    	}
    	return list;
	}

}
