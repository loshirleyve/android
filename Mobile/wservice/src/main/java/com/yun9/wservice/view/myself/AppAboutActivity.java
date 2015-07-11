package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/7/10.
 */
public class AppAboutActivity extends JupiterFragmentActivity{
    @ViewInject(id = R.id.setting_about_title)
    private JupiterTitleBarLayout aboutTitleLayout;

    public static void start(Activity activity){
        Intent intent = new Intent(activity, AppAboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aboutTitleLayout.setOnClickListener(onBackClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_app_about;
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
