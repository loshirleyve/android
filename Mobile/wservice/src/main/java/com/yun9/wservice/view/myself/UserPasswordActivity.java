package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by li on 2015/7/2.
 */
public class UserPasswordActivity extends JupiterFragmentActivity {
    private AlertDialog alertDialog;
    private String userid;
    private UserPasswordCommand command;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id = R.id.user_passwordTitle)
    private JupiterTitleBarLayout userPasswordTitle;

    @ViewInject(id = R.id.oldPassword)
    private EditText oldPassword;

    @ViewInject(id = R.id.newPassword)
    private EditText newPassword;

    @ViewInject(id = R.id.surePassword)
    private EditText surePassword;

    private boolean flag;

    public static void start(Activity activity, UserPasswordCommand command){
        Intent intent = new Intent(activity, UserPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserPasswordCommand.PARAM_PWD, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (UserPasswordCommand)getIntent().getSerializableExtra(UserPasswordCommand.PARAM_PWD);
        if(AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserId())){
            userid = command.getUserId();
            sessionManager.getUser().setId(userid);
        }else {
            userid = sessionManager.getUser().getId();
        }
        userPasswordTitle.getTitleLeft().setOnClickListener(onBackClickListener);
        userPasswordTitle.getTitleRight().setOnClickListener(onSureClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_password;
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           finish();
        }
    };

    public static boolean isPassword(String pwd){
        // 只允许字母和数字
        String pwdRegex = "^[a-zA-Z0-9]{6,15}$";
        return pwd.matches(pwdRegex);
    }

    private View.OnClickListener onSureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flag = isPassword(newPassword.getText().toString())  && (newPassword.getText().toString().equals(surePassword.getText().toString()));

            if(flag) {
                changeUserPwd();
            }else if(!isPassword(newPassword.getText().toString())) {
                Toast.makeText(mContext, getString(R.string.pass_prompt), Toast.LENGTH_SHORT).show();
            }else if (!newPassword.getText().toString().equals(surePassword.getText().toString())){
                Toast.makeText(mContext, getString(R.string.pass_promp), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void changeUserPwd(){
        if(AssertValue.isNotNull(sessionManager.getUser())){
            final Resource resource = resourceFactory.create("UpdatePasswd");
            resource.param("userid", userid);
            resource.param("oldPasswd", oldPassword.getText().toString());
            resource.param("newPasswd", newPassword.getText().toString());
            final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating));
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Toast.makeText(mContext, getString(R.string.pwd_upd_success), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, getString(R.string.pwd_upd_fail), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });
        }
    }
}

