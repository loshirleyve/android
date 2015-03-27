package com.yun9.mobile.framework.util;

import com.yun9.mobile.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class TipsUtil {
	public static void showToast(String msg, Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

	}

	public static void showToast(int resourceid, Context context) {
		Toast.makeText(context, resourceid, Toast.LENGTH_SHORT).show();

	}

	public static ProgressDialog openDialog(String msg, boolean cancel,
			Context context) {
		if (!AssertValue.isNotNullAndNotEmpty(msg)) {
			msg = context.getResources()
					.getText(R.string.default_progress_dialog_msg).toString();
		}

		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(cancel);

		progressDialog.show();
		return progressDialog;
	}

}
