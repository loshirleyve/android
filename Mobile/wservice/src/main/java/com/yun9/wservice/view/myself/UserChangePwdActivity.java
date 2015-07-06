package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/7/2.
 */
public class UserChangePwdActivity extends JupiterFragmentActivity {
    private String userid;
    private UserPwdCommand command;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id = R.id.user_passwordTitle)
    private JupiterTitleBarLayout userPwdTitleLayout;

    @ViewInject(id = R.id.oldPassword)
    private EditText oldPasswordEt;

    @ViewInject(id = R.id.newPassword)
    private EditText newPasswordEt;

    @ViewInject(id = R.id.surePassword)
    private EditText surePasswordEt;

    public static void start(Activity activity, UserPwdCommand command){
        Intent intent = new Intent(activity, UserChangePwdActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserPwdCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        command = (UserPwdCommand)getIntent().getSerializableExtra(UserPwdCommand.PARAM_COMMAND);
        if(AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserId())){
            userid = command.getUserId();
        }else {
            userid = sessionManager.getUser().getId();
        }
        userPwdTitleLayout.getTitleLeft().setOnClickListener(onBackClickListener);
        userPwdTitleLayout.getTitleRight().setOnClickListener(onSureClickListener);
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
            if(oldPasswordEt.getText().toString().equals("")){
                Toast.makeText(mContext, getString(R.string.old_pwd_input_pro), Toast.LENGTH_SHORT).show();
            }else if (newPasswordEt.getText().toString().equals("")){
                Toast.makeText(mContext, getString(R.string.new_pwd_input_pro), Toast.LENGTH_SHORT).show();
            }else if (surePasswordEt.getText().toString().equals("")){
                Toast.makeText(mContext, getString(R.string.sure_pwd_input_pro), Toast.LENGTH_SHORT).show();
            }else if(!isPassword(newPasswordEt.getText().toString())){
                Toast.makeText(mContext, getString(R.string.pwd_prompt), Toast.LENGTH_SHORT).show();
            }
            else if (!newPasswordEt.getText().toString().equals(surePasswordEt.getText().toString())){
                Toast.makeText(mContext, getString(R.string.pwd_promp), Toast.LENGTH_SHORT).show();
            }
            else {
                changeUserPwd();
            }
        }
    };

    private void changeUserPwd(){
        if(AssertValue.isNotNull(sessionManager.getUser())){
            final Resource resource = resourceFactory.create("UpdatePasswd");
            resource.param("userid", userid);
            resource.param("oldPasswd", oldPasswordEt.getText().toString());
            resource.param("newPasswd", newPasswordEt.getText().toString());
            final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.app_wating));
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Toast.makeText(mContext, getString(R.string.pwd_upd_success), Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });
        }
    }
}

