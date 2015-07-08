package com.yun9.wservice.support;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;

/**
 * Created by Leon on 15/7/8.
 */
public class OnLoginNoticeBindListener implements SessionManager.OnLoginListener {
    @Override
    public void login(User user) {
        //TODO 调用绑定服务
        //TODO 用户id暂时1
        DeviceManager deviceManager = JupiterApplication.getBeanManager().get(DeviceManager.class);
        String deviceid = deviceManager.getDevice().getId();
        String regid = (String) AppCache.getInstance().get("com.yun9.wservice.push.regid");

    }
}
