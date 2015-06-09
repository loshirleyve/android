package com.yun9.wservice.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterImageEditLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xia on 2015/5/20.
 */
public class LoginActivity extends JupiterFragmentActivity {

    private LoginCommand command;

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

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    public static void start(Activity activity, LoginCommand command) {
        Intent intent = new Intent(activity, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, LoginCommand.REQUEST_CODE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (LoginCommand) this.getIntent().getSerializableExtra("command");

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
                setResult(LoginCommand.RESULT_CODE_CANCEL);
                LoginActivity.this.finish();
            }
        });

        this.loginButton.setOnClickListener(loginButtonOnClickListener);
        this.passwordET.getTextET().setTransformationMethod(PasswordTransformationMethod.getInstance());

        if (AssertValue.isNotNull(command) && command.isDemo()) {
            this.userNoET.getTextET().setText("7959598");
            this.passwordET.getTextET().setText("linux12");
            this.login();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    private View.OnClickListener loginButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    private void login() {
        String userno = userNoET.getTextET().getText().toString();
        String password = passwordET.getTextET().getText().toString();

        if (!AssertValue.isNotNullAndNotEmpty(userno)) {
            Toast.makeText(LoginActivity.this, R.string.login_no_userno, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AssertValue.isNotNullAndNotEmpty(password)) {
            Toast.makeText(LoginActivity.this, R.string.login_no_password, Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);
        Resource resource = resourceFactory.create("CheckUserPassword");
        resource.param("userno", userno).param("passwd", password);

        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                try {
                    User user = (User) response.getPayload();
                    //登录成功
                    sessionManager.loginIn(user);
                    setResult(LoginCommand.RESULT_CODE_OK);
                    finish();
                } finally {
                    setLoading(false);
                }
            }

            @Override
            public void onFailure(Response response) {
                String cause = response.getCause();
                setLoading(false);
                //登录失败
                Toast.makeText(LoginActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setLoading(boolean statue) {
        loginButton.setLoading(statue);
        userNoET.getTextET().setEnabled(!statue);
        passwordET.getTextET().setEnabled(!statue);
    }

}
