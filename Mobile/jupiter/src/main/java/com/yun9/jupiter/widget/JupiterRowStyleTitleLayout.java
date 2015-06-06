package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/4/22.
 */
public class JupiterRowStyleTitleLayout extends JupiterRelativeLayout {

    private TextView titleTV;

    private ImageView mainIV;

    private ImageView arrowRightIV;

    private TextView hotNitoceTV;

    private ImageView selectModeIV;

    private boolean selected;

    private OnSelectListener onSelectListener;

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
        this.selectModeIV = (ImageView) this.findViewById(R.id.selectmode_iv);
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
            if (typedArray.hasValue(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleMainImage)){
                Drawable mainImage = typedArray.getDrawable(R.styleable.JupiterRowStyleTitleLayout_rowStyleTitleMainImage);
                if (mainImage != null){
                    this.mainIV.setImageDrawable(mainImage);
                }
            }
        }finally{
            typedArray.recycle();
        }
    }

    public void setSelectMode(boolean mode) {
        if (mode) {
            selected = false;
            this.selectModeIV.setVisibility(View.VISIBLE);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = !selected;
                    select(selected);
                    if (AssertValue.isNotNull(onSelectListener)) {
                        onSelectListener.onSelect(JupiterRowStyleTitleLayout.this, selected);
                    }
                }
            });
        } else {
            this.selectModeIV.setVisibility(View.GONE);
            this.setOnClickListener(null);
        }
    }

    public void select(boolean selected){
        this.selected = selected;

        if (this.selected) {
            selectModeIV.setImageResource(R.drawable.selector);
        } else {
            selectModeIV.setImageResource(R.drawable.selector_empty);
        }
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public void setTitleTV(TextView titleTV) {
        this.titleTV = titleTV;
    }

    public ImageView getMainIV() {
        return mainIV;
    }

    public void setMainIV(ImageView mainIV) {
        this.mainIV = mainIV;
    }

    public ImageView getArrowRightIV() {
        return arrowRightIV;
    }

    public void setArrowRightIV(ImageView arrowRightIV) {
        this.arrowRightIV = arrowRightIV;
    }

    public TextView getHotNitoceTV() {
        return hotNitoceTV;
    }

    public void setHotNitoceTV(TextView hotNitoceTV) {
        this.hotNitoceTV = hotNitoceTV;
    }

    public ImageView getSelectModeIV() {
        return selectModeIV;
    }

    public void setSelectModeIV(ImageView selectModeIV) {
        this.selectModeIV = selectModeIV;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }


}
