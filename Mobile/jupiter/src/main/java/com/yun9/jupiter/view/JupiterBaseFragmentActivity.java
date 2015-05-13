package com.yun9.jupiter.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.yun9.jupiter.bean.injected.BeanInjectedUtil;
import com.yun9.jupiter.view.injected.ViewInjectedUtil;

public abstract class JupiterBaseFragmentActivity extends FragmentActivity {

    private static boolean isShowToast = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
        initInjected(this);
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
        initInjected(this);
	}

	public void setContentView(View view) {
		super.setContentView(view);
        initInjected(this);
	}

	public static void initInjected(Activity activity){
        try {
            BeanInjectedUtil.initInjected(activity.getApplicationContext(),activity);
            ViewInjectedUtil.initInjected(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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

    protected abstract int getContentView();


}
