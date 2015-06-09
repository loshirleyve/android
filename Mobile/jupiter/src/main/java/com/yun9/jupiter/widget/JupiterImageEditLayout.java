package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/8.
 */
public class JupiterImageEditLayout extends JupiterRelativeLayout {

    private ImageView icoTV;

    private EditText textET;


    public JupiterImageEditLayout(Context context) {
        super(context);
    }

    public JupiterImageEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterImageEditLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_image_edit;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        icoTV = (ImageView) this.findViewById(R.id.ico_iv);
        textET = (EditText) this.findViewById(R.id.text_et);

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterImageEditLayout);

        try {
            if (typedArray.hasValue(R.styleable.JupiterImageEditLayout_image_ico_src)) {
                int icosrc = typedArray.getResourceId(R.styleable.JupiterImageEditLayout_image_ico_src, R.drawable.newpost);
                this.icoTV.setImageResource(icosrc);
            }

            if (typedArray.hasValue(R.styleable.JupiterImageEditLayout_image_edit_hint)) {
                String hint = typedArray.getString(R.styleable.JupiterImageEditLayout_image_edit_hint);
                this.textET.setHint(hint);
            }

        } finally {
            typedArray.recycle();
        }
    }


    public ImageView getIcoTV() {
        return icoTV;
    }

    public void setIcoTV(ImageView icoTV) {
        this.icoTV = icoTV;
    }

    public EditText getTextET() {
        return textET;
    }

    public void setTextET(EditText textET) {
        this.textET = textET;
    }
}
