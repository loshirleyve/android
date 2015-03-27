package com.yun9.mobile.calendar.impl;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.calendar.activity.ScheduleActivity;
import com.yun9.mobile.calendar.interfaces.ScheduleEntrance;

public class ImplScheduleEntrance implements ScheduleEntrance{

	private Activity activity;
	
	/**
	 * @param activity
	 */
	public ImplScheduleEntrance(Activity activity) {
		super();
		this.activity = activity;
	}


	@Override
	public void checkScheduleInfo() {
		Intent intent = new Intent(activity, ScheduleActivity.class);
		intent.putExtra("MODE", 1);
		activity.startActivity(intent);
	}


	@Override
	public void selectSchedule(String start, String end) {
		Intent intent = new Intent(activity, ScheduleActivity.class);
		intent.putExtra("MODE", 2);
		activity.startActivity(intent);
	}


}
