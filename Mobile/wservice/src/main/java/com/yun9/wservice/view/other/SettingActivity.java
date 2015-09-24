package com.yun9.wservice.view.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.view.myself.AppAboutActivity;

import java.io.File;

/**
 * Created by Leon on 15/6/9.
 */
public class SettingActivity extends JupiterFragmentActivity {


    @ViewInject(id = R.id.clean)
    private JupiterRowStyleSutitleLayout cleanLayout;

    @ViewInject(id = R.id.about)
    private JupiterRowStyleSutitleLayout aboutLayout;

    @ViewInject(id = R.id.logout)
    private JupiterRowStyleSutitleLayout logoutLayout;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @BeanInject
    private SessionManager sessionManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        logoutLayout.setOnClickListener(onLogoutClickListener);
        aboutLayout.setOnClickListener(onAboutClickListener);
        cleanLayout.getTitleTV()
                .setText(getResources().getString(R.string.setting_clean)
                        + getTotalDiskCache(ImageLoader.getInstance().getDiskCache().getDirectory()));

        cleanLayout.setOnClickListener(onCleanClickListener);
    }

    private String getTotalDiskCache(File file) {
        long kb = 1024;
        long mb = 1024*1024;
        String unit = "MB";
        long totalSize = getTotalSizeOfFilesInDir(file);
        if (totalSize > mb) {
            return " ("+totalSize/mb +"MB)";
        }
        return " ("+totalSize/kb +"KB)";
    }

    // 递归方式 计算文件的大小
    private long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    private View.OnClickListener onLogoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sessionManager.logout(sessionManager.getUser());
            ClientProxyCache.getInstance().removeProxy();
            Toast.makeText(mContext, getString(R.string.logout_notice),Toast.LENGTH_LONG).show();
            SettingActivity.this.finish();
        }
    };

    private View.OnClickListener onAboutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppAboutActivity.start(SettingActivity.this);
        }
    };

    private View.OnClickListener onCleanClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageLoader.getInstance().clearDiskCache();
            ImageLoader.getInstance().clearMemoryCache();
            FileCache.getInstance().clean();
            UserCache.getInstance().clean();
            InstCache.getInstance().clean();
            cleanLayout.getTitleTV()
                    .setText(getResources().getString(R.string.setting_clean)
                            + getTotalDiskCache(ImageLoader.getInstance().getDiskCache().getDirectory()));
        }
    };
}
