package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.util.Logger;
import com.yun9.mobile.annotation.BeanInject;


/**
 * Created by Leon on 15/4/16.
 */
public class JupiterTitleBarLayout extends JupiterRelativeLayout {

    private static final Logger logger = Logger.getLogger(JupiterTitleBarLayout.class);

    private LinearLayout titleLeft;

    private LinearLayout titleRight;

    private LinearLayout titleCenter;

    private TextView titleTv;

    private TextView titleSutitleTv;

    private TextView titleRightTv;

    private TextView titleLeftTv;


    @BeanInject
    private PropertiesManager propertiesManager;

    public JupiterTitleBarLayout(Context context) {
        super(context);
    }

    public JupiterTitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterTitleBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.title_bar;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.titleLeft = (LinearLayout) this.findViewById(R.id.title_left_ll);
        this.titleRight = (LinearLayout) this.findViewById(R.id.title_right_ll);
        this.titleCenter = (LinearLayout) this.findViewById(R.id.title_center_ll);
        this.titleTv = (TextView) this.findViewById(R.id.title_tv);
        this.titleSutitleTv = (TextView) this.findViewById(R.id.title_sutitle_tv);
        this.titleLeftTv = (TextView) this.findViewById(R.id.title_left_tv);
        this.titleRightTv = (TextView) this.findViewById(R.id.title_right_tv);

        this.initAttr(attrs);
        logger.d("title 初始化完成！");
    }

    private void initAttr(AttributeSet attrs){
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterTitleBarLayout);

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleText)) {
            String titleText = a.getString(R.styleable.JupiterTitleBarLayout_titleText);
            this.getTitleTv().setText(titleText);
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_sutitleText)){
            String sutitleText = a.getString(R.styleable.JupiterTitleBarLayout_sutitleText);
            this.getTitleSutitleTv().setText(sutitleText);
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleLeftText)){
            String leftText = a.getString(R.styleable.JupiterTitleBarLayout_titleLeftText);
            this.getTitleLeftTv().setText(leftText);
        }

        if(a.hasValue(R.styleable.JupiterTitleBarLayout_titleRightText)){
            String rightText = a.getString(R.styleable.JupiterTitleBarLayout_titleRightText);
            this.getTitleRightTv().setText(rightText);
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleTextVisibility)){
            boolean titleTextVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleLeftVisibility,false);
            if (titleTextVisibility){
                this.getTitleTv().setVisibility(View.VISIBLE);
            }else{
                this.getTitleTv().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_sutitleTextVisibility)){
            boolean sutitleTextVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_sutitleTextVisibility,false);
            if (sutitleTextVisibility){
                this.getTitleSutitleTv().setVisibility(View.VISIBLE);
            }else{
                this.getTitleSutitleTv().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleLeftVisibility)){
            boolean leftVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleLeftVisibility,false);
            if (leftVisibility){
                this.getTitleLeft().setVisibility(View.VISIBLE);
            }else{
                this.getTitleLeft().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleRightVisibility)){
            boolean rightVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleRightVisibility,false);
            if (rightVisibility){
                this.getTitleRight().setVisibility(View.VISIBLE);
            }else{
                this.getTitleRight().setVisibility(View.GONE);
            }
        }

        a.recycle();
    }

    public LinearLayout getTitleLeft() {
        return titleLeft;
    }

    public void setTitleLeft(LinearLayout titleLeft) {
        this.titleLeft = titleLeft;
    }

    public LinearLayout getTitleRight() {
        return titleRight;
    }

    public void setTitleRight(LinearLayout titleRight) {
        this.titleRight = titleRight;
    }

    public LinearLayout getTitleCenter() {
        return titleCenter;
    }

    public void setTitleCenter(LinearLayout titleCenter) {
        this.titleCenter = titleCenter;
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public void setTitleTv(TextView titleTv) {
        this.titleTv = titleTv;
    }

    public TextView getTitleSutitleTv() {
        return titleSutitleTv;
    }

    public void setTitleSutitleTv(TextView titleSutitleTv) {
        this.titleSutitleTv = titleSutitleTv;
    }

    public TextView getTitleRightTv() {
        return titleRightTv;
    }

    public void setTitleRightTv(TextView titleRightTv) {
        this.titleRightTv = titleRightTv;
    }

    public TextView getTitleLeftTv() {
        return titleLeftTv;
    }

    public void setTitleLeftTv(TextView titleLeftTv) {
        this.titleLeftTv = titleLeftTv;
    }
}
