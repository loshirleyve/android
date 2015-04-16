package com.yun9.wservice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/16.
 */
public class TitleBar extends JupiterRelativeLayout {

    private static final Logger logger = Logger.getLogger(TitleBar.class);


    @ViewInject(id=R.id.title_left_ll)
    private LinearLayout titleLeft;

    @ViewInject(id=R.id.title_right_ll)
    private LinearLayout titleRight;

    @ViewInject(id=R.id.title_center_ll)
    private LinearLayout titleCenter;

    @ViewInject(id=R.id.title_tv)
    private TextView titleTv;

    @ViewInject(id=R.id.title_sutitle_tv)
    private TextView titleSutitleTv;

    @ViewInject(id=R.id.title_right_tv)
    private TextView titleRightTv;

    @ViewInject(id=R.id.title_left_tv)
    private TextView titleLeftTv;

    private String titleText = "";

    private String sutitleText ="";

    private String rightBtnText = "";

    private String leftBtnText = "";

    @BeanInject
    private PropertiesManager propertiesManager;

    public TitleBar(Context context) {
        super(context);
        if (isInEditMode()) { return; }
        this.initView();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }
        this.initView();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) { return; }
        this.initView();
    }

    private void initView(){
        this.inflate(R.layout.title_bar);
        this.getTitleLeft().setVisibility(View.GONE);
        this.getTitleRight().setVisibility(View.GONE);
        this.setTitleText(this.titleText);
        this.setSutitleText(this.sutitleText);
        this.setRightBtnText(this.rightBtnText);

        logger.d("title 初始化完成！");
    }

    public LinearLayout getTitleLeft() {
        return titleLeft;
    }

    public LinearLayout getTitleRight() {
        return titleRight;
    }

    public LinearLayout getTitleCenter() {
        return titleCenter;
    }

    public String getSutitleText() {
        return sutitleText;
    }

    public void setSutitleText(int sutitleTextId) {
        this.setSutitleText(this.getContext().getString(sutitleTextId));
    }

    public void setSutitleText(String sutitleText) {
        this.sutitleText = sutitleText;
        this.titleSutitleTv.setText(sutitleText);
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(int titleTextId) {
        this.setTitleText(this.getContext().getResources().getString(titleTextId));
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
        this.titleTv.setText(titleText);
    }

    public String getRightBtnText() {
        return rightBtnText;
    }

    public void setRightBtnText(int rightBtnTextId) {
        this.setRightBtnText(this.getContext().getResources().getString(rightBtnTextId));
    }

    public void setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
        this.titleRightTv.setText(rightBtnText);

    }

    public String getLeftBtnText() {
        return leftBtnText;
    }
    public void setLeftBtnText(int leftBtnTextId) {
        this.setLeftBtnText(this.getContext().getString(leftBtnTextId));
    }
    public void setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
        this.titleLeftTv.setText(leftBtnText);
    }
}
