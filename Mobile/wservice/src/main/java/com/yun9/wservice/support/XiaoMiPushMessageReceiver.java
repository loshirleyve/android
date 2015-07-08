package com.yun9.wservice.support;

import android.content.Context;

import com.google.gson.JsonObject;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.manager.DeviceManager;
import com.yun9.jupiter.util.AppUtil;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.IntentIntegrator;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.util.Logger;
import com.yun9.wservice.MainApplication;
import com.yun9.wservice.model.PushMessageBean;
import com.yun9.wservice.view.main.MainActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/26.
 */
public class XiaoMiPushMessageReceiver extends PushMessageReceiver {

    private static final Logger logger = Logger.getLogger(XiaoMiPushMessageReceiver.class);

    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceiveMessage(final Context context, final MiPushMessage miPushMessage) {

        //如果程序不在前台则启动应用程序。
        if (!AppUtil.isRunningForeground(context)) {
            //如果应用改程序打开了，则切换到前台
            if (AppUtil.moveTaskToFront(context, context.getPackageName())) {
                MessageReceiverFactory messageReceiverFactory = MainApplication.getBeanManager().get(MessageReceiverFactory.class);
                messageReceiverFactory.sendMsg(context, miPushMessage.getContent());
            } else {
                //打开应用
                Map<String, String> params = new HashMap<>();
                params.put("push", miPushMessage.getContent());
                AppUtil.startApp(context, context.getPackageName(), params);
            }
        } else {
            MessageReceiverFactory messageReceiverFactory = MainApplication.getBeanManager().get(MessageReceiverFactory.class);
            messageReceiverFactory.sendMsg(context, miPushMessage.getContent());
        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();

        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        //String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                logger.d("mi push regid:" + mRegId);
                AppCache.getInstance().put("com.yun9.wservice.push.regid", mRegId);
                logger.d("-----------------mi push regid:" + mRegId);
            }
        }

        //下面是官方Demo代码。
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mStartTime = cmdArg1;
//                mEndTime = cmdArg2;
//            }
//        }
    }
}
