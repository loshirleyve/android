package com.yun9.wservice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.yun9.jupiter.view.JupiterBadgeView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/15.
 */
public class AlbumImageGridItem extends JupiterRelativeLayout {

    private ImageView imageView;

    private JupiterBadgeView selectBadgeView;

    private JupiterBadgeView deleteBadgeView;

    private ImageView successIV;

    private ImageView failureIV;

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
        successIV = (ImageView) this.findViewById(R.id.success_iv);
        failureIV = (ImageView) this.findViewById(R.id.failure_iv);

        selectBadgeView = new JupiterBadgeView(context, imageView);
        selectBadgeView.setBadgePosition(JupiterBadgeView.POSITION_BOTTOM_RIGHT);
        selectBadgeView.setBackgroundResource(R.drawable.selector);
        selectBadgeView.setBadgeSize(25, 25);

        deleteBadgeView = new JupiterBadgeView(context, imageView);
        deleteBadgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
        deleteBadgeView.setBackgroundResource(R.drawable.icn_delete);
        deleteBadgeView.setBadgeSize(25, 25);



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

    public JupiterBadgeView getSelectBadgeView() {
        return selectBadgeView;
    }

    public void setSelectBadgeView(JupiterBadgeView selectBadgeView) {
        this.selectBadgeView = selectBadgeView;
    }

    public JupiterBadgeView getDeleteBadgeView() {
        return deleteBadgeView;
    }

    public void setDeleteBadgeView(JupiterBadgeView deleteBadgeView) {
        this.deleteBadgeView = deleteBadgeView;
    }

    public ImageView getSuccessIV() {
        return successIV;
    }

    public void setSuccessIV(ImageView successIV) {
        this.successIV = successIV;
    }

    public void setSuccess(boolean success){
        if (success){
            successIV.setVisibility(View.VISIBLE);
            failureIV.setVisibility(View.GONE);
        }else{
            successIV.setVisibility(View.GONE);
            failureIV.setVisibility(View.VISIBLE);
        }
    }

    public void cleanState(){
        successIV.setVisibility(View.GONE);
        failureIV.setVisibility(View.GONE);
    }
}
