package com.yun9.mobile.framework.support;

import android.content.Context;
import android.content.Intent;
import com.yun9.mobile.framework.activity.PersonalDataActivity;
import com.yun9.mobile.framework.model.User;

/**
 * 进入个人资料实现
 */
public class GetPersonalData
{
    public static void goToPersonalData(Context context,User user,boolean isUser)
    {
        Intent intent = new Intent(context, PersonalDataActivity.class);
        intent.putExtra("user",user);
        intent.putExtra("isUser",isUser);
        context.startActivity(intent);
    }
}
