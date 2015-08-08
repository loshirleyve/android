package com.yun9.wservice.msghandler;

import android.app.Activity;
import android.view.View;

import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.model.Msg;

/**
 * Created by huangbinglong on 8/7/15.
 */
public interface MsgHandler {

    String getType();

    View getView(CustomCallbackActivity activity,Msg msg);

    void loadData(CustomCallbackActivity activity,View convertView,Msg msg);
}
