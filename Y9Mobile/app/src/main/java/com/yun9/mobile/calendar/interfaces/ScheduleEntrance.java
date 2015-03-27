package com.yun9.mobile.calendar.interfaces;

public interface ScheduleEntrance {

	/**
	 * 查看排班信息
	 */
	public void checkScheduleInfo();
	
	/**
	 * 选择班次
	 * @param start
	 * @param end
	 */
	public void selectSchedule(String start, String end);
}
