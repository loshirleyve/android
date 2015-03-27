package com.yun9.mobile.framework.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtil {
	private static final String NAME = "yun9Mobile";
	private static final String FIRST = "first";
	private static SpUtil instance;

	private SharedPreferences sp;

	// static {
	// instance = new SpUtil();
	// }

	private SpUtil() {
	}

	public static SpUtil getInstance(Context context) {
		if (instance == null) {
			instance = new SpUtil();
			SharedPreferences sp = instance.getSharePerference(context);
			instance.sp = sp;
		}
		return instance;
	}

	private SharedPreferences getSharePerference(Context context) {
		return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	public boolean isFirst() {
		return sp.getBoolean(FIRST, false);
	}

	public void setFirst(boolean first) {
		this.setBooleanSharedPerference(FIRST, first);
	}

	public void setStringSharedPerference(String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void setBooleanSharedPerference(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
