package com.yun9.jupiter.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/27.
 */
public class AppUtil {

    public static boolean moveTaskToFront(Context context, String packageName) {
        //获取ActivityManager
        ActivityManager mAm = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        //获得当前运行的task
        List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
            if (rti.topActivity.getPackageName().equals(packageName)) {
                mAm.moveTaskToFront(rti.id, 0);
                return true;
            }
        }
        return false;
    }

    public static void startApp(Context context, String packageName, Map<String, String> params) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (AssertValue.isNotNullAndNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        context.startActivity(intent);
    }

    //检查应用程序是否运行
    public static boolean isRunning(Context context) {
        boolean runing = false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();

        if (AssertValue.isNotNullAndNotEmpty(processes)) {
            for (ActivityManager.RunningAppProcessInfo rap : processes) {
                if (rap.processName.equals(context.getPackageName())) {
                    runing = true;
                    break;
                }
            }
        }

        return runing;
    }

    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();

        if (AssertValue.isNotNullAndNotEmpty(processes)) {
            for (ActivityManager.RunningAppProcessInfo rap : processes) {
                if (rap.processName.equals(context.getPackageName()) && rap.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
