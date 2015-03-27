package com.yun9.mobile.calendar.impl;

import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.calendar.interfaces.ScheduleEntrance;
import com.yun9.mobile.framework.base.activity.EnterActivity;

public class ImplEnterActivity implements EnterActivity {
	
	public Activity context ;
	private Map<String, Object> params;

	@Override
	public void enter(Activity context, Map<String, Object> params) {
		this.context= context;
		this.params = params;
		ScheduleEntrance schedule = new ImplScheduleEntrance(context);
		schedule.checkScheduleInfo();
	}
}
