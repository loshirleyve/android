package com.yun9.wservice.func.login;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by xia on 2015/5/20.
 */
public class LoginActivity extends JupiterActivity {

    @ViewInject(id= R.id.login03_title)
    private JupiterTitleBarLayout login03Title;

    @ViewInject(id=R.id.login_button)
    private Button loginButton;

    @ViewInject(id = R.id.login)
    private RelativeLayout login;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                return inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        login03Title.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity. this.finish();
            }
        });

        this.loginButton.setOnClickListener(loginButtonOnClickListener);
    }


    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void onClick(View v) {
                    loginButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.waiting),null);
        }
    };

}
