package com.yun9.jupiter.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yun9.jupiter.R;
import com.yun9.jupiter.widget.JupiterRelativeLayout;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Leon on 15/6/15.
 */
public class ImageBrowerPageItemWidget extends JupiterRelativeLayout {

    private PhotoView photoView;

    private ProgressBar loading;

    public ImageBrowerPageItemWidget(Context context) {
        super(context);
    }

    public ImageBrowerPageItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageBrowerPageItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_pager_image;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        photoView = (PhotoView) findViewById(R.id.iv_photo);
        loading = (ProgressBar) findViewById(R.id.loading);
    }

    public PhotoView getPhotoView() {
        return photoView;
    }

    public ProgressBar getLoading() {
        return loading;
    }
}
