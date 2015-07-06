package com.yun9.jupiter.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/7/6.
 */
public class IntentIntegrator {
    private final Context context;

    public static final List<String> TARGET_ALL_KNOWN = list(new String[]{"com.yun9.wservice"});
    private List<String> targetApplications = TARGET_ALL_KNOWN;


    private final Map<String, Object> moreExtras = new HashMap(3);


    public IntentIntegrator(Context context) {
        this.context = context;
    }


    public final void addExtra(String key, Object value) {
        this.moreExtras.put(key, value);
    }

    public final void start() {
        Intent intent = new Intent("com.yun9.wservice.view.MainActivity");
        intent.addCategory("android.intent.category.DEFAULT");
        String targetAppPackage1 = this.findTargetAppPackage(intent);

        if (AssertValue.isNotNullAndNotEmpty(targetAppPackage1)) {
            intent.setPackage(targetAppPackage1);
            intent.addFlags(67108864);
            intent.addFlags(524288);
            this.attachMoreExtras(intent);
            this.startActivity(intent);
        }
    }

    protected void startActivity(Intent intent) {
//        if (this.fragment == null) {
//            this.activity.startActivity(intent);
//        } else {
//            this.fragment.startActivity(intent);
//        }

        context.startActivity(intent);
    }


    private String findTargetAppPackage(Intent intent) {
        PackageManager pm = this.context.getPackageManager();
        List availableApps = pm.queryIntentActivities(intent, 65536);
        if (availableApps != null) {
            Iterator var4 = this.targetApplications.iterator();

            while (var4.hasNext()) {
                String targetApp = (String) var4.next();
                if (contains(availableApps, targetApp)) {
                    return targetApp;
                }
            }
        }

        return null;
    }


    private static boolean contains(Iterable<ResolveInfo> availableApps, String targetApp) {
        Iterator var2 = availableApps.iterator();

        String packageName;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            ResolveInfo availableApp = (ResolveInfo) var2.next();
            packageName = availableApp.activityInfo.packageName;
        } while (!targetApp.equals(packageName));

        return true;
    }

    private void attachMoreExtras(Intent intent) {
        Iterator var2 = this.moreExtras.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry entry = (Map.Entry) var2.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                intent.putExtra(key, (Long) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (Double) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (Float) value);
            } else if (value instanceof Bundle) {
                intent.putExtra(key, (Bundle) value);
            } else {
                intent.putExtra(key, value.toString());
            }
        }
    }

    private static List<String> list(String... values) {
        return Collections.unmodifiableList(Arrays.asList(values));
    }
}
