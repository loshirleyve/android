package com.yun9.mobile.camera.util;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yun9.mobile.R;

/**
 * 单例imageloader的基本设置
 */
public class SingletonImageLoader
{
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    private static class SingletonHolder {
        private static SingletonImageLoader instance = new SingletonImageLoader();
    }

    private SingletonImageLoader() {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.timeline_image_loading)
                .showImageForEmptyUri(R.drawable.timeline_image_failure)
                .showImageOnFail(R.drawable.timeline_image_failure)
//                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public static SingletonImageLoader getInstance() {
        return SingletonHolder.instance;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

    public DisplayImageOptions getOptions()
    {
        return options;
    }

    public ImageLoadingListener getListener(final ImageView imageView)
    {
        imageView.setBackgroundResource(R.drawable.load_anim);
        final AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        ImageLoadingListener loadingListener = new ImageLoadingListener()
        {
            @Override
            public void onLoadingStarted(String s, View view)
            {
                drawable.start();
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason)
            {
                drawable.stop();
                imageView.setBackgroundResource(R.drawable.timeline_image_failure);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap)
            {
                drawable.stop();
            }

            @Override
            public void onLoadingCancelled(String s, View view)
            {
                drawable.stop();
                imageView.setBackgroundResource(R.drawable.timeline_image_failure);
            }
        };
        return loadingListener;
    }
}
