package com.yun9.wservice.support;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.mobile.annotation.BeanInject;

import java.util.Collections;

/**
 * Created by Leon on 15/7/8.
 */
public class OnLoginNoticeBindListener implements SessionManager.OnLoginListener {
    @BeanInject
    ResourceFactory resourceFactory;
    @Override
    public void login(User user) {
        //TODO 调用绑定服务
        //TODO 用户id暂时1
        DeviceManager deviceManager = JupiterApplication.getBeanManager().get(DeviceManager.class);
        String deviceid = deviceManager.getDevice().getId();
        String regid = (String) AppCache.getInstance().get("com.yun9.wservice.push.regid");
        if(AssertValue.isNotNull(deviceid) && AssertValue.isNotNull(regid)){
            Resource resource = resourceFactory.create("NoticeUserPushBind");
            resource.param("userId", user.getId()).param("pushType", "xiaomi").param("deviceType", "android")
                    .param("deviceId", deviceid).param("params", Collections.singletonMap("regIds",Collections.singletonList(regid)));

            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    System.out.println("******************************************success");
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
}
