package com.yun9.wservice.func.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by xia on 2015/5/21.
 */
public class LoginMainActivity extends JupiterActivity{
    private static final Logger logger = Logger.getLogger(LoginMainActivity.class);

    @ViewInject(id = R.id.login_button_main)
    private Button loginButton;

    @ViewInject(id = R.id.login_title)
    private JupiterTitleBarLayout loginTitle;

    @ViewInject(id = R.id.free_reg)
    private TextView freeReg;

    @ViewInject(id = R.id.virtual_account)
    private TextView virtualAccount;

    private View.OnClickListener loginOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            logger.d("登录按钮被点击！");
            Intent i = new Intent(LoginMainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    };

    private View.OnClickListener freeOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            logger.d("免费注册按钮被点击！");
            Intent i = new Intent(LoginMainActivity.this, MailRegisterMainActivity.class);
            startActivity(i);
        }
    };
    private View.OnClickListener virtualOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            logger.d("虚拟账号按钮被点击！");
            Intent i = new Intent(LoginMainActivity.this, MailRegisterActivity.class);
            startActivity(i);
        }
    };

    public static void start(Context context, Bundle bundle){
        Intent intent = new Intent(context, LoginMainActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_main);
        super.onCreate(savedInstanceState);
        logger.d("初始化LoginActivity");
        loginButton.setOnClickListener(loginOnClickListener);
         this.freeReg.setOnClickListener(freeOnClickListener);
        this.virtualAccount.setOnClickListener(virtualOnClickListener);

        loginTitle.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginMainActivity.this.finish();
            }
        });
    }

}
