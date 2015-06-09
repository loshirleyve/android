package com.yun9.wservice.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by xia on 2015/5/21.
 */
public class LoginMainActivity extends JupiterFragmentActivity {
    private static final Logger logger = Logger.getLogger(LoginMainActivity.class);

    private LoginCommand command;

    @ViewInject(id = R.id.login_button_main)
    private Button loginButton;

    @ViewInject(id = R.id.login_title)
    private JupiterTitleBarLayout loginTitle;

    @ViewInject(id = R.id.free_reg)
    private TextView freeReg;

    @ViewInject(id = R.id.virtual_account)
    private TextView virtualAccount;

    private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("登录按钮被点击！");
            LoginActivity.start(LoginMainActivity.this, command);
        }
    };

    private View.OnClickListener freeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("免费注册按钮被点击！");
            Intent i = new Intent(LoginMainActivity.this, MailRegisterMainActivity.class);
            startActivity(i);
        }
    };
    private View.OnClickListener virtualOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("模拟账户按钮被点击！");
            LoginActivity.start(LoginMainActivity.this, command.setDemo(true));
        }
    };

    public static void start(Activity activity, LoginCommand command) {
        Intent intent = new Intent(activity, LoginMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, LoginCommand.REQUEST_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (LoginCommand) this.getIntent().getSerializableExtra("command");

        this.loginButton.setOnClickListener(loginOnClickListener);
        this.freeReg.setOnClickListener(freeOnClickListener);
        this.virtualAccount.setOnClickListener(virtualOnClickListener);

        loginTitle.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(LoginCommand.RESULT_CODE_CANCEL);
                LoginMainActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LoginCommand.REQUEST_CODE && resultCode == LoginCommand.RESULT_CODE_OK) {
            setResult(LoginCommand.RESULT_CODE_OK);
            finish();

        } else if (requestCode == LoginCommand.REQUEST_CODE && resultCode == LoginCommand.RESULT_CODE_CANCEL) {

        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_main;
    }

}
