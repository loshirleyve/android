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

    private String titleText = "";

    private String sutitleText ="";

    private String rightBtnText = "";

    private String leftBtnText = "";

    @BeanInject
    private PropertiesManager propertiesManager;

    public JupiterTitleBarLayout(Context context) {
        super(context);
        this.initView(null);
    }

    public JupiterTitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(attrs);
    }

    public JupiterTitleBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initView(attrs);
    }


    private void initView(AttributeSet attrs){
        this.inflate(R.layout.title_bar);
        if (isInEditMode()) { return; }

        this.titleLeft = (LinearLayout) this.findViewById(R.id.title_left_ll);
        this.titleRight = (LinearLayout) this.findViewById(R.id.title_right_ll);
        this.titleCenter = (LinearLayout) this.findViewById(R.id.title_center_ll);
        this.titleTv = (TextView) this.findViewById(R.id.title_tv);
        this.titleSutitleTv = (TextView) this.findViewById(R.id.title_sutitle_tv);
        this.titleLeftTv = (TextView) this.findViewById(R.id.title_left_tv);
        this.titleRightTv = (TextView) this.findViewById(R.id.title_right_tv);

        this.initAttr(attrs);

        this.getTitleLeft().setVisibility(View.GONE);
        this.getTitleRight().setVisibility(View.GONE);
        this.setTitleText(this.titleText);
        this.setSutitleText(this.sutitleText);
        this.setRightBtnText(this.rightBtnText);

        logger.d("title 初始化完成！");
    }


    private void initAttr(AttributeSet attrs){
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterTitleBarLayout);

        if (a.hasValue(R.styleable.JupiterTitleBarLayout_titleText)) {
            this.titleText = a.getString(R.styleable.JupiterTitleBarLayout_titleText);
        }

        a.recycle();
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
