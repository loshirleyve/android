package com.yun9.wservice.loadvaluehandler;

import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.model.User;
import com.yun9.wservice.cache.FileIdCache;
import com.yun9.wservice.cache.UserCache;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class LoadUserValueHandler implements FormUtilFactory.LoadValueHandler{

    @Override
    public void load(String id, FormUtilFactory.LoadValueCompleted callback) {
        User user = UserCache.getInstance().getUser(id);
        if (user == null) {
            // 从服务器获取
            return;
        }
        callback.callback(user);
    }
}
