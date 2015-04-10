package com.yun9.wservice.util;

import android.util.Log;

public class Logger {

	private static Boolean DEBUG = true;

	private Class<?> TAG;

	private Logger(Class<?> clazz) {
		this.TAG = clazz;
	}

	public static Logger getLogger(Class<?> clazz) {
		Logger logger = new Logger(clazz);
		return logger;

	}

	public static void setDebug(Boolean value) {
		DEBUG = value;
	}

	public void i(String msg) {
		if (DEBUG) {
			Log.i(this.TAG.getName(), msg);
		}
	}

	public void e(String msg) {
		if (DEBUG) {
			Log.e(this.TAG.getName(), msg);
		}
	}

	public void d(String msg) {
		if (DEBUG) {
			Log.d(this.TAG.getName(), msg);
		}
	}

	public void exception(Exception e) {
		if (DEBUG) {
			e.printStackTrace();
		}
	}
}
