package com.yun9.wservice.widget;

import android.app.Activity;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yun9.wservice.view.common.SimpleBrowserActivity;
import com.yun9.wservice.view.common.SimpleBrowserCommand;

/**
 * Created by huangbinglong on 16/1/15.
 */
public class Y9UrlSpan extends ClickableSpan {

    private Activity activity;
    private SimpleBrowserCommand command;

    public Y9UrlSpan(Activity activity,SimpleBrowserCommand command) {
        this.activity = activity;
        this.command = command;
    }
    @Override
    public void onClick(View widget) {
        SimpleBrowserActivity.start(this.activity,this.command);
    }
}
