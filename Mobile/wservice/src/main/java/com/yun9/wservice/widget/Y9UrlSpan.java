package com.yun9.wservice.widget;

import android.app.Activity;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.wservice.manager.support.Base64;
import com.yun9.wservice.manager.support.MD5;
import com.yun9.wservice.view.common.SimpleBrowserActivity;
import com.yun9.wservice.view.common.SimpleBrowserCommand;

import java.util.Date;

/**
 * Created by huangbinglong on 16/1/15.
 */
public class Y9UrlSpan extends ClickableSpan {

    private Activity activity;
    private SimpleBrowserCommand command;
    private String originUrl;

    private static String SECRET_CODE_PARAM_NAME = "secretCode";

    private static String SECRET_KEY = "_wservice_";

    public Y9UrlSpan(Activity activity,SimpleBrowserCommand command) {
        this.activity = activity;
        this.command = command;
    }
    @Override
    public void onClick(View widget) {
        if (!AssertValue.isNotNullAndNotEmpty(originUrl)) {
            originUrl = this.command.getUrl();
        }
        this.command.setUrl(appendSecretCode(originUrl));
        SimpleBrowserActivity.start(this.activity,this.command);
    }

    private String appendSecretCode(String orginUrl) {
        if(!AssertValue.isNotNullAndNotEmpty(orginUrl) || orginUrl.indexOf("&"+SECRET_CODE_PARAM_NAME) > 0) {
            return orginUrl;
        }
        SessionManager sessionManager = JupiterApplication.getBeanManager().get(SessionManager.class);

        StringBuffer sb = new StringBuffer("{\"key\":");
        sb.append("\"" + MD5.getMessageDigest((DateFormatUtil.format(new Date(),"yyyyMMddHHmm")+SECRET_KEY).getBytes())+"\",");
        sb.append("\"userno\":").append("\"" + sessionManager.getUser().getNo() + "\"}");
        orginUrl += "&"+SECRET_CODE_PARAM_NAME+"="+ Base64.encode(sb.toString().getBytes());
        return orginUrl;
    }
}
