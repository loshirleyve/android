package com.yun9.wservice.support;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.mobile.annotation.BeanInject;

/**
 * Created by Leon on 15/7/8.
 */
public class OnLogoutUnNoticeBindListener implements SessionManager.OnLogoutListener {
    @BeanInject
    ResourceFactory resourceFactory;

    private static final Logger logger = Logger.getLogger(OnLogoutUnNoticeBindListener.class);
    @Override
    public void logout(User user) {
        //TODO 调用解除绑定服务
        logger.d("解除用户:" + user.getId() + "消息推送绑定!");
        DeviceManager deviceManager = JupiterApplication.getBeanManager().get(DeviceManager.class);
        String deviceid = deviceManager.getDevice().getId();

        Resource resource = resourceFactory.create("NoticeUserPushUnbind");
        resource.param("deviceId", deviceid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                System.out.println("*****************************************UNBindsuccess");
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
