package com.yun9.wservice.view.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.login.LoginActivity;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;

/**
 * Created by xia on 2015/5/22.
 */
public class UserRegisterCompleteActivity extends JupiterFragmentActivity {
    private String command;
    private LoginCommand loginCommand;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.user_first_logging)
    private Button logBtn;

    public static void start(Activity activity, String command) {
        Intent intent = new Intent(activity, UserRegisterCompleteActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("command", command);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = getIntent().getStringExtra("command");
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterCompleteActivity.this.finish();
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(onLoginClickListener);

        logBtn.setOnClickListener(onLoginClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register_user_complete;
    }

    private View.OnClickListener onLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!AssertValue.isNotNull(loginCommand)) {
                loginCommand = new LoginCommand();
            }
            loginCommand.setUserno(command).setNewRegisterUser(true);
            Intent i = new Intent(UserRegisterCompleteActivity.this, LoginActivity.class);
            i.putExtra("command", loginCommand);
            startActivity(i);
        }
    };
}
