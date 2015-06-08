package com.yun9.wservice.view.login;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterImageEditLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by xia on 2015/5/20.
 */
public class LoginActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titlebar;

    @ViewInject(id = R.id.login_btn)
    private JupiterImageButtonLayout loginButton;

    @ViewInject(id = R.id.userno_et)
    private JupiterImageEditLayout userNoET;

    @ViewInject(id = R.id.password_et)
    private JupiterImageEditLayout passwordET;

    @ViewInject(id = R.id.login)
    private RelativeLayout login;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        titlebar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

        this.loginButton.setOnClickListener(loginButtonOnClickListener);
        this.passwordET.getTextET().setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setLoading(true);
        }
    };

    private void setLoading(boolean statue) {
        loginButton.setLoading(statue);
        userNoET.getTextET().setEnabled(!statue);
        passwordET.getTextET().setEnabled(!statue);


    }

}
