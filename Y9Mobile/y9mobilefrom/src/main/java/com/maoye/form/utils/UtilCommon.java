package com.maoye.form.utils;

import android.content.Context;
import android.widget.Toast;

public class UtilCommon {
	public static void showToast(Context context, String text){
		Toast.makeText(context, text, 0).show();
	}
}
