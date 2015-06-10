package com.yun9.wservice.view.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;

/**
 * Created by xia on 2015/5/22.
 */
public class UserRegisterCompleteActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, UserRegisterCompleteActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterCompleteActivity.this.finish();
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserRegisterCompleteActivity.this, LoginMainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register_user_complete;
    }

}
