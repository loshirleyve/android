package com.yun9.jupiter.actvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.yun9.jupiter.JupiterApplication;
import com.yun9.jupiter.context.AppContext;
import com.yun9.jupiter.util.AssertValue;

public abstract class BaseActivity extends Activity {
	private static boolean isShowToast = true;

	protected Context context;

    protected AppContext appContext;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;

        JupiterApplication application = (JupiterApplication)this.getApplication();

        this.appContext  = application.getAppContext();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (AssertValue.isNotNull(progressDialog)) {
			progressDialog.dismiss();
		}
	}

	protected Context getContext() {
		return this.context;
	}

	protected void openDialog() {
		this.openDialog(null);
	}

	protected void openDialog(String msg) {
		this.openDialog(msg, false);
	}

	protected void openDialog(String msg, boolean cancel) {
//		if (!AssertValue.isNotNullAndNotEmpty(msg)) {
//			msg = this.context.getResources()
//					.getText(R.string.default_progress_dialog_msg).toString();
//		}

		if (!AssertValue.isNotNull(progressDialog)) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(msg);
			progressDialog.setCancelable(cancel);
		}

		progressDialog.show();
	}

	protected void hideDialog() {
		if (AssertValue.isNotNull(progressDialog)) {
			// progressDialog.hide();
			progressDialog.dismiss();
		}
	}

	protected void showToast(String msg) {
		if (isShowToast) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	protected void showToast(int msg) {
		if (isShowToast) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

}
