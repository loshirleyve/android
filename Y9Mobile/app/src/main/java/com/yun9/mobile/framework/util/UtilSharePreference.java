package com.yun9.mobile.framework.util;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.session.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class UtilSharePreference {

	public static final String TAG = UtilSharePreference.class.getSimpleName();
	/**
	 * 每个用户都有自己的SharedPreferences
	 * 
	 * @param fileName
	 * @param mode
	 * @param context
	 * @return
	 */
	public static SharedPreferences getCurrentUserSharedPreference(String fileName, int mode, Context context)
	{
		String userid;
		try {
			SessionManager session = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
			userid = session.getAuthInfo().getUserinfo().getId();
	
		} catch (Exception e) {
			userid = "youke";
		}
		fileName = userid + "-" + fileName;
		SharedPreferences sp = context.getSharedPreferences(fileName, mode);
		return sp;
	}
	
	
	/**
	 * 
	 *  每个用户都有自己的SharedPreferences
	 * @param fileName
	 * @param mode
	 * @return sp
	 * 
	 */
	public static SharedPreferences getCurrentUserSharedPreference(String fileName, int mode)
	{
		Context context = BeanConfig.getInstance().getBeanContext().getApplicationContext();
		return getCurrentUserSharedPreference(fileName, mode, context);
	}
	
	public static Boolean putStringPreferences(SharedPreferences sp, String key, String value)
	{

		try {
			Editor ed = sp.edit();
			ed.putString(key, value);
			ed.commit();
			return true;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static Boolean putBoolPreferences(SharedPreferences sp, String key, boolean value)
	{

		try {
			Editor ed = sp.edit();
			ed.putBoolean(key, value);
			ed.commit();
			return true;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}

}
