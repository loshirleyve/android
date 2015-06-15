package com.yun9.jupiter.util;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import com.yun9.jupiter.R;
import com.yun9.jupiter.cache.FileIdCache;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangbinglong on 15/5/22.
 */
public class Y9ImageDownloader extends BaseImageDownloader {

    private static final Logger logger = Logger.getLogger(Y9ImageDownloader.class);

    public Y9ImageDownloader(Context context) {
        super(context);
    }

    public Y9ImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        imageUri = FileIdCache.getInstance().getAsString(imageUri);
        if (AssertValue.isNotNullAndNotEmpty(imageUri)) {
            return getStreamFromNetwork(imageUri, extra);
        } else {
            // 如果没有找到缓冲的图片，返回空图片
            return getStreamFromDrawable("drawable://" + R.drawable.ic_empty, extra);
        }

    }
}
