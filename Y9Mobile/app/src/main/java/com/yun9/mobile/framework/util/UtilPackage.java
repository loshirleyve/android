package com.yun9.mobile.framework.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class UtilPackage {

	public static String getAppVersion(Context context){
		String version = "";
		
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	
	/**
	 * 安装APK
	 * @param apk
	 */
	public static void installAPK(File apk, Context context) {
	  Intent intent = new Intent();
	  intent.setAction("android.intent.action.VIEW");
	  intent.addCategory("android.intent.category.DEFAULT");
	  intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
	  context.startActivity(intent);
	  
	}
	
}
