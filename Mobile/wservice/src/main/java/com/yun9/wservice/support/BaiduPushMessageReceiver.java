package com.yun9.wservice.support;

import android.content.Context;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.yun9.jupiter.util.Logger;

import java.util.List;

/**
 * Created by Leon on 15/5/26.
 */
public class BaiduPushMessageReceiver extends FrontiaPushMessageReceiver {
    private static final Logger logger = Logger.getLogger(BaiduPushMessageReceiver.class);

    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
        logger.d(s);
        logger.d(s1);
        logger.d(s2);
        logger.d(s3);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        logger.d(s);
        logger.d(s1);
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
