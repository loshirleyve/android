package com.yun9.wservice.loadvaluehandler;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.wservice.cache.FileIdCache;
import com.yun9.wservice.cache.UserCache;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class LoadUserValueHandler implements FormUtilFactory.LoadValueHandler{

    @Override
    public void load(String id, final FormUtilFactory.LoadValueCompleted callback) {
        final User user = UserCache.getInstance().getUser(id);
        if (user == null) {
            ResourceFactory resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid",id);
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    User user = (User) response.getPayload();
                    callback.callback(user);
                }

                @Override
                public void onFailure(Response response) {
                    System.out.println(response.getCause());
                }

                @Override
                public void onFinally(Response response) {

                }
            });
            return;
        }
        callback.callback(user);
    }
}
