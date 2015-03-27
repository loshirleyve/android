package com.yun9.mobile.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import android.util.Log;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.resources.Resource;

/**
 * 记录异常至数据库
 * 
 * @author yun9
 * 
 */
public class SysCrashLogger {

	public static void log(final Throwable t) {
		log(t, null);
	}

	public static void log(final Throwable t,
			final Map<String, Object> deviceInfo) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Resource resource = ResourceUtil.get("SysCrashSaveService");
					resource.setFromService(true);
					resource.param("exception", t.getClass().getName());
					resource.param("devicetype", "android");
					resource.param("message", getStackTrace(t));
					resource.param("type", "unknow");
					if (AssertValue.isNotNull(deviceInfo)) {
						resource.param("deviceinfo",
								JsonUtil.beanToJson(deviceInfo));
					}
					resource.invok(emptyCallback);
				} catch (Exception e) {
					Log.e(SysCrashLogger.class.getSimpleName(), "记录异常日志异常", e);
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	private static AsyncHttpResponseCallback emptyCallback = new AsyncHttpResponseCallback() {
		@Override
		public void onSuccess(Response response) {
		}
		
		@Override
		public void onFailure(Response response) {
		}
	};
	

	private static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
}
