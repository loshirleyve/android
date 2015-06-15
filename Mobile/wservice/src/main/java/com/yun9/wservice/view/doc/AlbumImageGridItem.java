package com.yun9.wservice.view.doc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/15.
 */
public class AlbumImageGridItem extends JupiterRelativeLayout {

    private ImageView imageView;

    private ImageView selectImageView;

    private ProgressBar progressBar;

    public AlbumImageGridItem(Context context) {
        super(context);
    }

    public AlbumImageGridItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlbumImageGridItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_album_image_grid_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        imageView = (ImageView) this.findViewById(R.id.image);
        progressBar = (ProgressBar) this.findViewById(R.id.progress);
        selectImageView = (ImageView) this.findViewById(R.id.select_iv);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ImageView getSelectImageView() {
        return selectImageView;
    }
}
