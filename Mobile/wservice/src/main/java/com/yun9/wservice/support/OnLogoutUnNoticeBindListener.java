package com.yun9.wservice.support;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.Logger;

/**
 * Created by Leon on 15/7/8.
 */
public class OnLogoutUnNoticeBindListener implements SessionManager.OnLogoutListener {

    private static final Logger logger = Logger.getLogger(OnLogoutUnNoticeBindListener.class);

    @Override
    public void logout(User user) {
        DeviceManager deviceManager = JupiterApplication.getBeanManager().get(DeviceManager.class);
        ResourceFactory resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
        String deviceid = deviceManager.getDevice().getSerial();
        Resource resource = resourceFactory.create("NoticeUserPushUnbind");
        resource.param("deviceId", deviceid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }
}
