package com.yun9.wservice.view.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.view.myself.AppAboutActivity;

/**
 * Created by Leon on 15/6/9.
 */
public class SettingActivity extends JupiterFragmentActivity {


//    @ViewInject(id = R.id.clean)
//    private JupiterRowStyleSutitleLayout cleanLayout;

    @ViewInject(id = R.id.about)
    private JupiterRowStyleSutitleLayout aboutLayout;

    @ViewInject(id = R.id.logout)
    private JupiterRowStyleSutitleLayout logoutLayout;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @BeanInject
    private SessionManager sessionManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        logoutLayout.setOnClickListener(onLogoutClickListener);

        aboutLayout.setOnClickListener(onAboutClickListener);
    }

    private View.OnClickListener onLogoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sessionManager.logout(sessionManager.getUser());
            ClientProxyCache.getInstance().removeProxy();
            SettingActivity.this.finish();
        }
    };

    private View.OnClickListener onAboutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppAboutActivity.start(SettingActivity.this);
        }
    };
}
