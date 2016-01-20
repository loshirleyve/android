package com.yun9.wservice.widget;

import android.app.Activity;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.manager.support.Base64;
import com.yun9.wservice.view.common.SimpleBrowserActivity;
import com.yun9.wservice.view.common.SimpleBrowserCommand;

import java.util.Date;

/**
 * Created by huangbinglong on 16/1/15.
 */
public class Y9UrlSpan extends ClickableSpan {

    private Activity activity;
    private SimpleBrowserCommand command;

    private static String __SECRET_CODE_PARAM_NAME = "secretCode";

    public Y9UrlSpan(Activity activity,SimpleBrowserCommand command) {
        this.activity = activity;
        this.command = command;
    }
    @Override
    public void onClick(View widget) {
        this.command.setUrl(appendSecretCode(this.command.getUrl()));
        SimpleBrowserActivity.start(this.activity,this.command);
    }

    private String appendSecretCode(String orginUrl) {
        if(!AssertValue.isNotNullAndNotEmpty(orginUrl) || orginUrl.indexOf("&"+__SECRET_CODE_PARAM_NAME) > 0) {
            return orginUrl;
        }
        SessionManager sessionManager = JupiterApplication.getBeanManager().get(SessionManager.class);

        StringBuffer sb = new StringBuffer("{\"date\":");
        sb.append("\"" + new Date().getTime()+"\",");
        sb.append("\"userno\":").append("\"" + sessionManager.getUser().getNo() + "\"}");
        orginUrl += "&"+__SECRET_CODE_PARAM_NAME+"="+ Base64.encode(sb.toString().getBytes());
        return orginUrl;
    }
}
