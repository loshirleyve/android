package com.yun9.jupiter.push.support;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

//import com.baidu.android.pushservice.PushConstants;
//import com.baidu.android.pushservice.PushManager;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.push.PushFactory;

/**
 * Created by Leon on 15/5/26.
 */
public class BaiduPushFactory implements PushFactory, Bean, Initialization {

    @Override
    public Class<?> getType() {
        return PushFactory.class;
    }


    @Override
    public void init(BeanManager beanManager) {
        //初始化Push
        //FrontiaApplication.initFrontiaApplication(beanManager.getApplicationContext());
    }

    public void start(Context context) {
        // Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
        // 这里把apikey存放于manifest文件中，只是一种存放方式，
        // 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
        // "api_key")
        //请将AndroidManifest.xml 128 api_key 字段值修改为自己的 api_key 方可使用 ！！
        //ATTENTION：You need to modify the value of api_key to your own at row 128 in AndroidManifest.xml to use this Demo !!
//        PushManager.startWork(context,
//                PushConstants.LOGIN_TYPE_API_KEY,
//                this.getMetaValue(context, "api_key"));
        // Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
        // PushManager.enableLbs(getApplicationContext());
    }

    @Override
    public void stop(Context context) {

    }


    // 获取ApiKey
    private static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

}
