package com.yun9.mobile.department.support;

import android.app.Activity;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： SelectContactUser 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-11-21下午5:09:33 修改人：ruanxiaoyu 修改时间：2014-11-21下午5:09:33 修改备注：
 * 
 * @version
 * 
 */
public class SelectContactUserFactory {
	
	public static SelectContactUser create(Activity activity) {

		return new SelectContactUserImpl(activity);
	}
}
