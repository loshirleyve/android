package com.yun9.wservice;


import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.wservice.support.OnLoginNoticeBindListener;
import com.yun9.wservice.support.OnLogoutUnNoticeBindListener;


public class MainApplication extends JupiterApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //注册解除推送绑定动作
        SessionManager sessionManager = this.getBeanManager().get(SessionManager.class);
        sessionManager.regOnLogoutListener(new OnLogoutUnNoticeBindListener());
        sessionManager.regOnLoginListener(new OnLoginNoticeBindListener());
    }

}
