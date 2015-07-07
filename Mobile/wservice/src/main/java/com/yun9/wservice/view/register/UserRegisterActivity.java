package com.yun9.wservice.view.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageEditLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xia on 2015/5/21.
 */
public class UserRegisterActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.register)
    private RelativeLayout registerRL;

    @ViewInject(id = R.id.email_et)
    private JupiterImageEditLayout imageEditLayout;

    @BeanInject
    private ResourceFactory resourceFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerRL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterActivity.this.finish();
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = imageEditLayout.getTextET().getText().toString();

                if (AssertValue.isNotNullAndNotEmpty(email) && isEmail(email)) {
                    String tipsText = getResources().getString(R.string.app_wating);

                    final ProgressDialog registerDialog = ProgressDialog.show(UserRegisterActivity.this, null, tipsText, true);

                    Resource resource = resourceFactory.create("RegisteUser");
                    resource.param("userno", email).param("email", email).param("state", "unactivated");

                    resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                        @Override
                        public void onSuccess(Response response) {
                            UserRegisterActivity.this.finish();
                            UserRegisterCompleteActivity.start(UserRegisterActivity.this);
                        }

                        @Override
                        public void onFailure(Response response) {
                            Toast.makeText(UserRegisterActivity.this,response.getCause(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinally(Response response) {
                            registerDialog.dismiss();
                        }
                    });


                } else {
                    Toast.makeText(UserRegisterActivity.this, R.string.register_user_input_email_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register_user;
    }

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
