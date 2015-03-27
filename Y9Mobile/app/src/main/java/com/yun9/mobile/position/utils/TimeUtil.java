package com.yun9.mobile.position.utils;

import java.util.Calendar;

public class TimeUtil {

	
	
	/**
	 * 
	 * 
	 * @return 当前时间(格式  2014-8-8 13:40:40)
	 */
	public static String getDayTime() {
		Calendar calendar = Calendar.getInstance();
		String time = calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + " "
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"
				+ calendar.get(Calendar.SECOND);

		return time;
	}
	
	
	/**
	 * 获取时间 long 数字
	 * @return
	 */
	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		

		return calendar.getTime().getTime()+"";

	}

}
