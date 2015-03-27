package com.yun9.mobile.product.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class DialogUtil {
	/**
	 * 提示对话框
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 加载对话框
	 * @param context
	 * @param title
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String title) {
		ProgressDialog progressDialog;
    	progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正加载，请稍候......");
		return progressDialog;
    }

}
