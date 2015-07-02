package com.yun9.wservice.view.myself;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/7/2.
 */
public class UserPasswordActivity extends JupiterFragmentActivity {
    public static void start(Activity activity, UserInfoCommand command){
        Intent intent = new Intent(activity, UserPasswordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserInfoCommand.PARAM_USER_INFO_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_user_password;
    }
}
