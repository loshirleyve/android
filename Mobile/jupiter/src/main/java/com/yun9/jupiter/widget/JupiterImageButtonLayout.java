package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/5/29.
 */
public class JupiterImageButtonLayout extends JupiterRelativeLayout {
    private ImageView icoIV;

    private TextView textTV;

    public JupiterImageButtonLayout(Context context) {
        super(context);
    }

    public JupiterImageButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterImageButtonLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_image_button;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        icoIV = (ImageView) findViewById(R.id.ico_iv);
        textTV = (TextView) findViewById(R.id.text_tv);

        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterImageButtonLayout);

        if (a.hasValue(R.styleable.JupiterImageButtonLayout_imageButtonIcoSrc)) {
            int icosrc = a.getResourceId(R.styleable.JupiterImageButtonLayout_imageButtonIcoSrc,R.drawable.newpost);
            this.icoIV.setImageResource(icosrc);
        }

        if (a.hasValue(R.styleable.JupiterImageButtonLayout_imageButtonText)){
            String text = a.getString(R.styleable.JupiterImageButtonLayout_imageButtonText);
            this.textTV.setText(text);
        }
    }

    public ImageView getIcoIV() {
        return icoIV;
    }

    public TextView getTextTV() {
        return textTV;
    }
}
