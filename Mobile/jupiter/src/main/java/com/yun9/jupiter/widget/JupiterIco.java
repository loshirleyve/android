package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterIco extends JupiterRelativeLayout {

    private TextView icoTextView;

    private ImageView icoImageView;

    private LinearLayout icoLinearLayout;

    public JupiterIco(Context context) {
        super(context);
    }

    public JupiterIco(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterIco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_ico;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.icoTextView = (TextView) findViewById(R.id.ico_tv);
        this.icoImageView = (ImageView) findViewById(R.id.ico_iv);
        this.icoLinearLayout = (LinearLayout) findViewById(R.id.ico_ll);

        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterIco);

        try {
            if (a.hasValue(R.styleable.JupiterIco_icoShowText)) {
                boolean showText = a.getBoolean(R.styleable.JupiterIco_icoShowText, true);

                if (showText) {
                    this.icoTextView.setVisibility(View.VISIBLE);
                } else {
                    this.icoTextView.setVisibility(View.GONE);
                }

            }

        } finally {
            a.recycle();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.icoLinearLayout.setOnClickListener(l);
    }
}
