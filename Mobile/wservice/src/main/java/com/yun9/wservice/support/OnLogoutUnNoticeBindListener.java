package com.yun9.wservice.support;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.Logger;

/**
 * Created by Leon on 15/7/8.
 */
public class OnLogoutUnNoticeBindListener implements SessionManager.OnLogoutListener {
    private static final Logger logger = Logger.getLogger(OnLogoutUnNoticeBindListener.class);
    @Override
    public void logout(User user) {
        //TODO 调用解除绑定服务
        logger.d("解除用户:" + user.getId() + "消息推送绑定!");

    }
}
