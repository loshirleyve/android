package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.yun9.jupiter.R;


/**
 * Created by Leon on 15/4/21.
 */
public class JupiterRowStyleSutitleLayout extends JupiterRelativeLayout{
    private TextView titleTV;

    private ImageView mainIV;

    private TextView sutitleTv;

    private TextView timeTv;

    private ImageView arrowRightIV;

    public JupiterRowStyleSutitleLayout(Context context) {
        super(context);
    }

    public JupiterRowStyleSutitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterRowStyleSutitleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.row_style_sutitle;
    }

    protected void initViews(Context context, AttributeSet attrs, int defStyle){
        this.mainIV = (ImageView) this.findViewById(R.id.main_iv);
        this.setTitleTV((TextView) this.findViewById(R.id.title_tv));
        this.sutitleTv = (TextView) this.findViewById(R.id.sutitle_tv);
        this.arrowRightIV = (ImageView) this.findViewById(R.id.arrow_right_iv);
        this.timeTv = (TextView) this.findViewById(R.id.time_tv);

        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterRowStyleSutitleLayout);

        try{
            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleTitleText)){
                String titleText = typedArray.getString(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleTitleText);

                this.getTitleTV().setText(titleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleText)){
                String sutitleText= typedArray.getString(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleTitleText);
                this.getSutitleTv().setText(sutitleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowMainImage)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowMainImage,false);

                if (showMainImage){
                    this.mainIV.setVisibility(View.VISIBLE);
                }else{
                    this.mainIV.setVisibility(View.GONE);
                }
            }

//        if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowHotText)){
//            boolean showHotText = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowHotText,false);
//        }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowTimeText)){
                boolean showTimeText = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowTimeText,false);

                if (showTimeText){
                    this.timeTv.setVisibility(View.VISIBLE);
                }else{
                    this.timeTv.setVisibility(View.GONE);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrow)){
                boolean showArrowImage = typedArray.getBoolean(R.styleable.JupiterRowStyleSutitleLayout_rowStyleSutitleShowArrow,false);

                if (showArrowImage){
                    this.arrowRightIV.setVisibility(View.VISIBLE);
                }else{
                    this.arrowRightIV.setVisibility(View.GONE);
                }
            }
        }finally{
            typedArray.recycle();
        }




    }

    public ImageView getMainIV() {
        return mainIV;
    }

    public void setMainIV(ImageView mainIV) {
        this.mainIV = mainIV;
    }


    public TextView getSutitleTv() {
        return sutitleTv;
    }

    public void setSutitleTv(TextView sutitleTv) {
        this.sutitleTv = sutitleTv;
    }

    public ImageView getArrowRightIV() {
        return arrowRightIV;
    }

    public void setArrowRightIV(ImageView arrowRightIV) {
        this.arrowRightIV = arrowRightIV;
    }

    public TextView getTimeTv() {
        return timeTv;
    }

    public void setTimeTv(TextView timeTv) {
        this.timeTv = timeTv;
    }


    public TextView getTitleTV() {
        return titleTV;
    }

    public void setTitleTV(TextView titleTV) {
        this.titleTV = titleTV;
    }
}
