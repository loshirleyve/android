package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/4/22.
 */
public class JupiterRowStyleTitleLayout extends JupiterRelativeLayout {

    private TextView titleTV;

    private ImageView mainIV;

    private ImageView arrowRightIV;

    private TextView hotNitoceTV;

    public JupiterRowStyleTitleLayout(Context context) {
        super(context);
    }

    public JupiterRowStyleTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterRowStyleTitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return  R.layout.row_style_title;
    }

    protected void initViews(Context context, AttributeSet attrs, int defStyle){

        this.titleTV = (TextView) this.findViewById(R.id.title_tv);
        this.mainIV = (ImageView) this.findViewById(R.id.main_iv);
        this.arrowRightIV = (ImageView) this.findViewById(R.id.arrow_right_iv);
        this.hotNitoceTV = (TextView) this.findViewById(R.id.hot_notice);
        this.initAttr(attrs);
    }


    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterRowStyleTitleLayout);

        try{
            if (typedArray.hasValue(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleText)){
                String titleText = typedArray.getString(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleText);
                this.titleTV.setText(titleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowArrow)){
                boolean showArrow = typedArray.getBoolean(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowArrow, false);
                if (showArrow){
                    this.arrowRightIV.setVisibility(View.VISIBLE);
                }else{
                    this.arrowRightIV.setVisibility(View.GONE);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowHotText)){
                boolean showHotText = typedArray.getBoolean(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowHotText,false);
                if (showHotText){
                    this.hotNitoceTV.setVisibility(View.VISIBLE);
                }else{
                    this.hotNitoceTV.setVisibility(View.GONE);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowMainImage)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleShowMainImage,false);
                if (showMainImage){
                    this.mainIV.setVisibility(View.VISIBLE);
                }else{
                    this.mainIV.setVisibility(View.GONE);
                }
            }
        }finally{
            typedArray.recycle();
        }


    }


}
