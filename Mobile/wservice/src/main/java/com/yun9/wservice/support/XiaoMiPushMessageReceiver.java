package com.yun9.wservice.support;

import android.content.Context;

import com.google.gson.JsonObject;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.wservice.MainApplication;
import com.yun9.wservice.model.PushMessageBean;

/**
 * Created by Leon on 15/5/26.
 */
public class XiaoMiPushMessageReceiver extends PushMessageReceiver {

    private static final Logger logger = Logger.getLogger(XiaoMiPushMessageReceiver.class);

    @Override
    public void onReceiveMessage(final Context context, final MiPushMessage miPushMessage) {

        //如果程序不在前台则启动应用程序。
        if (!AppUtil.isRunningForeground(context)) {
            //激活应用程序到前台
            AppUtil.startApp(context,context.getPackageName());
            logger.d("应用程序没有在前台！");
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    processMsg(context,miPushMessage);
                }
            }).start();
        }else{
            this.processMsg(context,miPushMessage);
        }

        logger.d(miPushMessage.getTitle());
    }

    private void processMsg(Context context,MiPushMessage miPushMessage){
        JsonObject obj = null;
        String type = null;
        try{
            obj = JsonUtil.fromString(miPushMessage.getContent());
            type = obj.get("type").getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }

        if (AssertValue.isNotNull(obj) && AssertValue.isNotNullAndNotEmpty(type)){
            PushMessageBean messageBean = new PushMessageBean();
            messageBean.setContent(obj);
            messageBean.setDesc(miPushMessage.getDescription());
            messageBean.setTitle(miPushMessage.getTitle());
            messageBean.setType(type.trim());

            MessageReceiverFactory messageReceiverFactory = MainApplication.getBeanManager().get(MessageReceiverFactory.class);
            messageReceiverFactory.sendMsg(context,messageBean);
        }else{
            logger.d("无法识别的推送消息！");
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        logger.d("111");
    }
}
