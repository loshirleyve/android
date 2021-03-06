package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    private RelativeLayout titleRight;

    private LinearLayout titleCenter;

    private ImageView titleCenterIV;

    private TextView titleTv;

    private TextView titleSutitleTv;

    private TextView titleRightTv;

    private ImageView titleRightIV;

    private TextView titleLeftTv;

    private ImageView titleLeftIV;


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
        this.titleRight = (RelativeLayout) this.findViewById(R.id.title_right_ll);
        this.titleCenter = (LinearLayout) this.findViewById(R.id.title_center_ll);
        this.titleCenterIV = (ImageView) this.findViewById(R.id.title_center_iv);
        this.titleTv = (TextView) this.findViewById(R.id.title_tv);
        this.titleSutitleTv = (TextView) this.findViewById(R.id.title_sutitle_tv);
        this.titleLeftTv = (TextView) this.findViewById(R.id.title_left_tv);
        this.titleRightTv = (TextView) this.findViewById(R.id.title_right_tv);
        this.titleRightIV = (ImageView) this.findViewById(R.id.title_right_iv);
        this.titleLeftIV = (ImageView) this.findViewById(R.id.title_left_iv);

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
            boolean titleTextVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleTextVisibility, false);
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

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleLeftTextVisibility)){
            boolean leftVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleLeftTextVisibility,false);
            if (leftVisibility){
                this.getTitleLeftTv().setVisibility(View.VISIBLE);
            }else{
                this.getTitleLeftTv().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleLeftIcoVisibility)){
            boolean leftVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleLeftIcoVisibility,false);
            if (leftVisibility){
                this.getTitleLeftIV().setVisibility(View.VISIBLE);
            }else{
                this.getTitleLeftIV().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleRightTextVisibility)){
            boolean rightTextVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleRightTextVisibility,false);
            if (rightTextVisibility){
                this.getTitleRightTv().setVisibility(View.VISIBLE);
            }else{
                this.getTitleRightTv().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleRightIcoVisibility)){
            boolean rightIcoVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleRightIcoVisibility,false);
            if (rightIcoVisibility){
                this.getTitleRightIV().setVisibility(View.VISIBLE);
            }else{
                this.getTitleRightIV().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleCenterIcoVisibility)){
            boolean centerIcoVisibility = a.getBoolean(R.styleable.JupiterTitleBarLayout_titleCenterIcoVisibility,false);
            if (centerIcoVisibility){
                this.getTitleCenterIV().setVisibility(View.VISIBLE);
            }else{
                this.getTitleCenterIV().setVisibility(View.GONE);
            }
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleLeftIcoSrc)){
            int resourceid = a.getResourceId(R.styleable.JupiterTitleBarLayout_titleLeftIcoSrc, R.drawable.title_left_btn_return);
            this.getTitleLeftIV().setImageResource(resourceid);
        }


        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleRightIcoSrc)){
            int resourceid = a.getResourceId(R.styleable.JupiterTitleBarLayout_titleRightIcoSrc,R.drawable.classification);
            this.getTitleRightIV().setImageResource(resourceid);
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_backgroungColor)){
            int resourceid = a.getColor(R.styleable.JupiterTitleBarLayout_backgroungColor, R.color.title_color);
            this.findViewById(R.id.title_bar_rl).setBackgroundColor(resourceid);
        }

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleCenterIcoSrc)){
            int resourceid = a.getResourceId(R.styleable.JupiterTitleBarLayout_titleCenterIcoSrc, R.drawable.arrow_down_grey);
            this.getTitleCenterIV().setImageResource(resourceid);
        }

        a.recycle();
    }

    public LinearLayout getTitleLeft() {
        return titleLeft;
    }

    public void setTitleLeft(LinearLayout titleLeft) {
        this.titleLeft = titleLeft;
    }

    public RelativeLayout getTitleRight() {
        return titleRight;
    }

    public void setTitleRight(RelativeLayout titleRight) {
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

    public ImageView getTitleLeftIV() {
        return titleLeftIV;
    }

    public void setTitleLeftIV(ImageView titleLeftIV) {
        this.titleLeftIV = titleLeftIV;
    }

    public ImageView getTitleRightIV() {
        return titleRightIV;
    }

    public void setTitleRightIV(ImageView titleRightIV) {
        this.titleRightIV = titleRightIV;
    }

    public ImageView getTitleCenterIV() {
        return titleCenterIV;
    }

    public void setTitleCenterIV(ImageView titleCenterIV) {
        this.titleCenterIV = titleCenterIV;
    }
}
