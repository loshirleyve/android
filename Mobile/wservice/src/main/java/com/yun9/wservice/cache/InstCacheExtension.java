package com.yun9.wservice.cache;

import android.util.Log;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.model.BigInst;

/**
 * Created by huangbinglong on 15/8/27.
 */
public class InstCacheExtension extends InstCache {

    private static InstCacheExtension instance;

    private InstCacheExtension() {
        super();
    }

    public static InstCacheExtension getInstance() {
        synchronized (InstCacheExtension.class) {
            if (instance == null) {
                instance = new InstCacheExtension();
            }
        }
        return instance;
    }

    public void getInst(String instid, final InstCacheExtensionCallback callback) {
        CacheInst cacheInst = null;
        if (AssertValue.isNotNullAndNotEmpty(instid)) {
            cacheInst = this.get(instid, CacheInst.class);
        }
        // 没有callback，则直接返回
        if (callback == null){
            return;
        }
        // 从缓冲中得到了机构信息
        if (cacheInst != null){
            callback.callback(cacheInst);
            return;
        }
        Resource resource = JupiterApplication.getBeanManager().get(ResourceFactory.class)
                .create("QueryInstInfoService");
        resource.param("instid",instid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                BigInst bigInst = (BigInst) response.getPayload();
                CacheInst inst = new CacheInst().setLogo(bigInst.getLogo())
                                    .setSimplename(bigInst.getSimplename())
                                    .setInstid(bigInst.getId())
                                    .setInstname(bigInst.getName())
                                    .setTel(bigInst.getTel());
                callback.callback(inst);
            }

            @Override
            public void onFailure(Response response) {
                Log.e(InstCacheExtension.class.getName(),response.getCause());
                callback.callback(null);
            }

            @Override
            public void onFinally(Response response) {
            }
        });
    }
}
