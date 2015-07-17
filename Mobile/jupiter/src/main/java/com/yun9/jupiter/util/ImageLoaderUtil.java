package com.yun9.jupiter.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yun9.jupiter.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageLoaderUtil {
    private static ImageLoaderUtil instance;

    private DisplayImageOptions options;

    private Map<Integer,DisplayImageOptions> customErrorImageOptions;

    private ImageLoaderUtil() {
    }

    public static ImageLoaderUtil getInstance(Context context) {
        if (!AssertValue.isNotNull(instance)) {
            instance = new ImageLoaderUtil();
            instance.init(context);
        }

        return instance;
    }

    private void init(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        //config.threadPoolSize(1);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.memoryCache(new LruMemoryCache(50 * 1024 * 1024));
        //config.memoryCache(new WeakMemoryCache());
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app
        config.imageDownloader(new Y9ImageDownloader(context)); // 设置我们自己的imageDownloader，支持文件ID格式：y9fileid://1234
        customErrorImageOptions = new HashMap<>();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_loading)
                .showImageForEmptyUri(R.drawable.icon_empty)
                .showImageOnFail(R.drawable.icon_failed)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                //.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .imageScaleType(ImageScaleType.EXACTLY)

//                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(8))
                        .build();
        config.defaultDisplayImageOptions(options);

        ImageLoader.getInstance().init(config.build());
    }

    public void displayImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    /**
     *
     * @param uri 图片URL
     * @param imageView 图片ImageView
     * @param errorImage 默认加载失败的图片，失败或空，都将使用这个图片
     */
    public void displayImage(String uri, ImageView imageView,int errorImage) {
        DisplayImageOptions temp = customErrorImageOptions.get(errorImage);
        if (temp == null){
            temp = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_loading)
                    .showImageForEmptyUri(errorImage)
                    .showImageOnFail(errorImage)
                    .cacheInMemory(false)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(8))
                    .build();
            customErrorImageOptions.put(errorImage,temp);
        }
        ImageLoader.getInstance().displayImage(uri, imageView, temp);
    }

    public void displayImage(String uri, ImageView imageView, SimpleImageLoadingListener simpleImageLoadingListener) {
        ImageLoader.getInstance().displayImage(uri, imageView, options, simpleImageLoadingListener);
    }

    public void displayImage(String uri, ImageView imageView, SimpleImageLoadingListener simpleImageLoadingListener, ImageLoadingProgressListener imageLoadingProgressListener) {
        ImageLoader.getInstance().displayImage(uri, imageView, options, simpleImageLoadingListener, imageLoadingProgressListener);
    }

}
