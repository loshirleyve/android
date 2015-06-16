package com.yun9.wservice.view.doc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/16.
 */
public class FileItemWidget extends JupiterRelativeLayout {

    private ImageView icoImaveView;

    private TextView fileNameTV;

    private TextView fileTimeTV;

    private TextView fileSizeTV;

    private LinearLayout stateLL;

    private ImageView selectStateIV;

    private ImageView unSelectStateIV;

    private ImageView deleteStateIV;

    public FileItemWidget(Context context) {
        super(context);
    }

    public FileItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FileItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_local_file_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        icoImaveView = (ImageView) findViewById(R.id.ico_iv);
        fileNameTV = (TextView) findViewById(R.id.filename_tv);
        fileTimeTV  = (TextView) findViewById(R.id.file_time_tv);
        fileSizeTV = (TextView) findViewById(R.id.file_size_tv);
        stateLL = (LinearLayout) findViewById(R.id.state_ll);
        selectStateIV = (ImageView) findViewById(R.id.select_state_iv);
        unSelectStateIV = (ImageView) findViewById(R.id.unselect_state_iv);
        deleteStateIV = (ImageView) findViewById(R.id.delete_state_iv);
    }

    public void selected(boolean selected){
        if (selected) {
            this.getSelectStateIV().setVisibility(View.VISIBLE);
            this.getUnSelectStateIV().setVisibility(View.GONE);
        } else {

            this.getSelectStateIV().setVisibility(View.GONE);
            this.getUnSelectStateIV().setVisibility(View.VISIBLE);
        }
    }

    public void selectMode(boolean mode){

        if (mode) {
            this.getSelectStateIV().setVisibility(View.GONE);
            this.getUnSelectStateIV().setVisibility(View.VISIBLE);
        }else{
            this.getSelectStateIV().setVisibility(View.GONE);
            this.getUnSelectStateIV().setVisibility(View.GONE);
        }
    }

    public ImageView getIcoImaveView() {
        return icoImaveView;
    }

    public TextView getFileNameTV() {
        return fileNameTV;
    }

    public TextView getFileTimeTV() {
        return fileTimeTV;
    }

    public TextView getFileSizeTV() {
        return fileSizeTV;
    }

    public LinearLayout getStateLL() {
        return stateLL;
    }

    public ImageView getSelectStateIV() {
        return selectStateIV;
    }

    public ImageView getUnSelectStateIV() {
        return unSelectStateIV;
    }

    public ImageView getDeleteStateIV() {
        return deleteStateIV;
    }
}
