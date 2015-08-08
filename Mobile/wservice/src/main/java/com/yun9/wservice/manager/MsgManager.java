package com.yun9.wservice.manager;

import android.app.Activity;
import android.view.View;

import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.model.Msg;

/**
 * Created by huangbinglong on 8/7/15.
 */
public interface MsgManager {

    View getView(CustomCallbackActivity activity,View convertView,Msg msg);
}
