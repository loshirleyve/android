package com.yun9.wservice.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 8/21/15.
 */
public class LeftRightTextWidget extends JupiterRelativeLayout{

    private TextView leftTv;

    private TextView rightTv;

    public LeftRightTextWidget(Context context) {
        super(context);
    }

    public LeftRightTextWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeftRightTextWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_left_right_text;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        leftTv = (TextView) this.findViewById(R.id.left_tv);
        rightTv = (TextView) this.findViewById(R.id.right_tv);
    }

    public TextView getLeftTv() {
        return leftTv;
    }

    public LeftRightTextWidget setLeftTv(TextView leftTv) {
        this.leftTv = leftTv;
        return this;
    }

    public TextView getRightTv() {
        return rightTv;
    }

    public LeftRightTextWidget setRightTv(TextView rightTv) {
        this.rightTv = rightTv;
        return this;
    }
}
