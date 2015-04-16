package com.yun9.jupiter.actvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.util.AssertValue;

public abstract class JupiterActivity extends Activity {

    private ProgressDialog progressDialog;

    private static boolean isShowToast = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JupiterApplication jupiterApplication = (JupiterApplication) this.getApplicationContext();

        try {
            jupiterApplication.getBeanManager().initInjected(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (AssertValue.isNotNull(progressDialog)) {
            progressDialog.dismiss();
        }
    }

    public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initInjectedView(this);
	}


	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initInjectedView(this);
	}


	public void setContentView(View view) {
		super.setContentView(view);
		initInjectedView(this);
	}
	

	public static void initInjectedView(Activity activity){
		ActivityUtil.initInjectedView(activity, activity.getApplicationContext(), activity.getWindow().getDecorView());
	}




    protected void openDialog() {
        this.openDialog(null);
    }

    protected void openDialog(String msg) {
        this.openDialog(msg, false);
    }

    protected void openDialog(String msg, boolean cancel) {
        if (!AssertValue.isNotNull(progressDialog)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(cancel);
        }

        progressDialog.show();
    }

    protected void hideDialog() {
        if (AssertValue.isNotNull(progressDialog)) {
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
