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

/**
 * Created by Leon on 15/5/29.
 */
public class JupiterImageButtonLayout extends JupiterRelativeLayout {
    private ImageView icoIV;

    private TextView textTV;

    private LinearLayout buttonLL;

    private RelativeLayout buttonBarRL;

    private LinearLayout loadingLL;

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
        buttonLL = (LinearLayout) findViewById(R.id.button_ll);
        buttonBarRL = (RelativeLayout) findViewById(R.id.button_rl);
        loadingLL = (LinearLayout) findViewById(R.id.loading_view);


        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterImageButtonLayout);

        try{
            if (a.hasValue(R.styleable.JupiterImageButtonLayout_imageButtonIcoSrc)) {
                int icosrc = a.getResourceId(R.styleable.JupiterImageButtonLayout_imageButtonIcoSrc,R.drawable.newpost);
                this.icoIV.setImageResource(icosrc);
            }

            if (a.hasValue(R.styleable.JupiterImageButtonLayout_imageButtonText)){
                String text = a.getString(R.styleable.JupiterImageButtonLayout_imageButtonText);
                this.textTV.setText(text);
            }

            if (a.hasValue(R.styleable.JupiterImageButtonLayout_imageButtonShowIco)){
                boolean showIco  = a.getBoolean(R.styleable.JupiterImageButtonLayout_imageButtonShowIco,false);

                if (showIco){
                    icoIV.setVisibility(View.VISIBLE);
                }else{
                    icoIV.setVisibility(View.GONE);
                }
            }
        }finally{
            a.recycle();
        }

    }

    public void setLoading(boolean state){
        if (state){
            this.loadingLL.setVisibility(View.VISIBLE);
            buttonBarRL.setEnabled(false);
        }else{
            this.loadingLL.setVisibility(View.GONE);
            buttonBarRL.setEnabled(true);
        }
    }

    public ImageView getIcoIV() {
        return icoIV;
    }

    public TextView getTextTV() {
        return textTV;
    }

    public LinearLayout getButtonLL() {
        return buttonLL;
    }

    public void setButtonLL(LinearLayout buttonLL) {
        this.buttonLL = buttonLL;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.buttonBarRL.setOnClickListener(l);
    }
}
