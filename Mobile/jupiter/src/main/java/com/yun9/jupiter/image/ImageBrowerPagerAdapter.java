package com.yun9.jupiter.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yun9.jupiter.R;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerPagerAdapter extends PagerAdapter {

    private List<FileBean> fileBeans;
    private Context mContext;

    public ImageBrowerPagerAdapter(Context context, List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(fileBeans)) {
            return fileBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final ImageBrowerPageItemWidget imageBrowerPageItemWidget = new ImageBrowerPageItemWidget(mContext);
        FileBean fileBean = fileBeans.get(position);
        imageBrowerPageItemWidget.setTag(fileBean);

        CharSequence text = mContext.getResources().getString(R.string.jupiter_viewpager_indicator, position + 1, fileBeans.size());

        imageBrowerPageItemWidget.getPageNoTV().setText(text);


        container.addView(imageBrowerPageItemWidget, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //尝试查找原版本
        String path = FileCache.getInstance().getFileUrl(fileBean.getId());
        if (!AssertValue.isNotNullAndNotEmpty(path)) {
            path = fileBean.getFilePath();
        }

        ImageLoaderUtil.getInstance(mContext).displayImage(path, imageBrowerPageItemWidget.getPhotoView(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                imageBrowerPageItemWidget.getLoading().setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                imageBrowerPageItemWidget.getLoading().setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                imageBrowerPageItemWidget.getLoading().setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
                imageBrowerPageItemWidget.getLoading().setVisibility(View.GONE);
            }
        });

        return imageBrowerPageItemWidget;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
