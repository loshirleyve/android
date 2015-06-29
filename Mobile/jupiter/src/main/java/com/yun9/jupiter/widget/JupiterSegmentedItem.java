package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.Logger;

/**
 * Created by Leon on 15/4/30.
 */
public class JupiterSegmentedItem extends JupiterRelativeLayout {

    private static final Logger logger = Logger.getLogger(JupiterSegmentedItem.class);

    private boolean clicked = false;

    private ImageView bootomDirectionIV;

    private ImageView icoIV;

    private TextView titleTextTV;

    private TextView descTextTV;

    private int textColor;

    private int textColorSelected;

    private int icoImage;

    private int icoImageSelected;

    private int postion = 0;


    public JupiterSegmentedItem(Context context) {
        super(context);
        if (isInEditMode()) { return; }
        this.initEvents();

    }

    public JupiterSegmentedItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }
        this.initEvents();
    }

    public JupiterSegmentedItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) { return; }
        this.initEvents();
    }

    @Override
    protected int getContextView() {
        return R.layout.segmented_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        bootomDirectionIV = (ImageView) this.findViewById(R.id.bootom_direction);
        icoIV = (ImageView) this.findViewById(R.id.segmented_item_ico_iv);
        titleTextTV = (TextView) this.findViewById(R.id.segmented_item_title_tv);
        descTextTV = (TextView) this.findViewById(R.id.segmented_item_desc_tv);

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterSegmentedItem);

        try{
            if (typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_titleText)){
                String titleText = typedArray.getString(R.styleable.JupiterSegmentedItem_segmented_item_titleText);
                this.titleTextTV.setText(titleText);
            }

            if(typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_descText)){
                String descText = typedArray.getString(R.styleable.JupiterSegmentedItem_segmented_item_descText);
                this.descTextTV.setText(descText);
            }

            if (typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_text_color)){
                this.textColor = typedArray.getColor(R.styleable.JupiterSegmentedItem_segmented_item_text_color,R.color.devide_line);
                this.titleTextTV.setTextColor(this.textColor);
                this.descTextTV.setTextColor(this.textColor);
            }

            if (typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_text_color_selected)){
                this.textColorSelected = typedArray.getColor(R.styleable.JupiterSegmentedItem_segmented_item_text_color_selected,R.color.title_color);
            }

            if (typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_ico)){
                this.icoImage = typedArray.getResourceId(R.styleable.JupiterSegmentedItem_segmented_item_ico,R.drawable.user_head);
                this.icoIV.setImageResource(this.icoImage);
            }

            if (typedArray.hasValue(R.styleable.JupiterSegmentedItem_segmented_item_ico_selected)){
                this.icoImageSelected = typedArray.getResourceId(R.styleable.JupiterSegmentedItem_segmented_item_ico_selected,R.drawable.user_head);
            }

        }finally{
            typedArray.recycle();
        }

    }

    private void initEvents(){
    }



    public void setClicked(boolean clicked){
        this.clicked = clicked;
        if (clicked){
            bootomDirectionIV.setVisibility(View.VISIBLE);
            this.titleTextTV.setTextColor(this.textColorSelected);
            this.descTextTV.setTextColor(this.textColorSelected);
            this.icoIV.setImageResource(this.icoImageSelected);
        }else{
            bootomDirectionIV.setVisibility(View.GONE);
            this.titleTextTV.setTextColor(this.textColor);
            this.descTextTV.setTextColor(this.textColor);
            this.icoIV.setImageResource(this.icoImage);
        }
    }

    public ImageView getIcoIV() {
        return icoIV;
    }

    public void setIcoIV(ImageView icoIV) {
        this.icoIV = icoIV;
    }

    public TextView getTitleTextTV() {
        return titleTextTV;
    }

    public void setTitleTextTV(TextView titleTextTV) {
        this.titleTextTV = titleTextTV;
    }

    public TextView getDescTextTV() {
        return descTextTV;
    }

    public void setDescTextTV(TextView descTextTV) {
        this.descTextTV = descTextTV;
    }

    public void setIcoImage(int icoImage) {
        this.icoImage = icoImage;
    }

    public void setIcoImageSelected(int icoImageSelected) {
        this.icoImageSelected = icoImageSelected;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
