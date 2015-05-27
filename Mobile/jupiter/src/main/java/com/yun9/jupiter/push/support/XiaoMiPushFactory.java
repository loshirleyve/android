package com.yun9.jupiter.push.support;

import android.app.ActivityManager;
import android.content.Context;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.push.PushFactory;
import com.yun9.jupiter.util.Logger;

import java.util.List;

/**
 * Created by Leon on 15/5/26.
 */
public class XiaoMiPushFactory implements PushFactory,Bean,Initialization {
    private final static Logger logger = Logger.getLogger(XiaoMiPushFactory.class);


    @Override
    public void start(Context context) {
        if (this.shouldInit(context)){
            MiPushClient.registerPush(context, "2882303761517339536", "5561733985536");
            String regid = MiPushClient.getRegId(context);
            //发现部分手机(小米4)第一次执行此代码时获取不到regid，但是第二次就可以取得了。
            logger.d("MI Push注册成功，regid:"+regid);

        }
    }

    @Override
    public void stop(Context context) {
        MiPushClient.unregisterPush(context);
    }


    @Override
    public Class<?> getType() {
        return PushFactory.class;
    }


    private boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(BeanManager beanManager) {
        this.start(beanManager.getApplicationContext());
    }
}
