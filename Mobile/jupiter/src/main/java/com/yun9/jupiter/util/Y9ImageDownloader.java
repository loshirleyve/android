package com.yun9.jupiter.util;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.model.CacheFile;

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
        CacheFile cacheFile = FileCache.getInstance().getFile(imageUri);
        String newUrl = "";
        if (AssertValue.isNotNull(cacheFile)) {
            newUrl = cacheFile.getThumbnailUrl();

            if (!AssertValue.isNotNullAndNotEmpty(newUrl)){
                newUrl = cacheFile.getUrl();
            }

        }


        if (AssertValue.isNotNullAndNotEmpty(newUrl)) {
            return getStreamFromNetwork(newUrl, extra);
        } else {
            throw new UnsupportedOperationException(String.format("UIL doesn\'t support scheme(protocol) by default [%s]. You should implement this support yourself (BaseImageDownloader.getStreamFromOtherSource(...))", new Object[]{imageUri}));
        }
    }
}
