package com.yun9.wservice.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.register.UserRegisterActivity;

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

    private LoginCommand loginMainCommand;

    private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("登录按钮被点击！");

            if (!AssertValue.isNotNull(loginMainCommand)) {
                loginMainCommand = new LoginCommand();
            }
            LoginActivity.start(LoginMainActivity.this, loginMainCommand.setDemo(false));
        }
    };

    private View.OnClickListener freeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("免费注册按钮被点击！");
            Intent i = new Intent(LoginMainActivity.this, UserRegisterActivity.class);
            startActivity(i);
        }
    };
    private View.OnClickListener virtualOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("模拟账户按钮被点击！");
            if (!AssertValue.isNotNull(loginMainCommand)) {
                loginMainCommand = new LoginCommand();
            }
            LoginActivity.start(LoginMainActivity.this, loginMainCommand.setDemo(true));
        }
    };

    public static void start(Activity activity, LoginCommand command) {
        Intent intent = new Intent(activity, LoginMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LoginCommand.PARAM_COMMAND, command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (LoginCommand) this.getIntent().getSerializableExtra(LoginCommand.PARAM_COMMAND);

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

        if (AssertValue.isNotNull(loginMainCommand) && loginMainCommand.getRequestCode() == requestCode && resultCode == LoginCommand.RESULT_CODE_OK) {
            setResult(LoginCommand.RESULT_CODE_OK);
            finish();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login_main;
    }

}
