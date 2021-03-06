package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterBadgeView;

/**
 * Created by huangbinglong on 15/6/2.
 */
public class JupiterTextIco extends JupiterEditableView{

    public static final int DEFAULT_IMAGE_SIZE = 60;

    private ImageView itemImage;
    private TextView itemName;
    private JupiterBadgeView badgeView;
    private int errorImage = -1;

    public JupiterTextIco(Context context) {
        super(context);
    }

    public JupiterTextIco(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterTextIco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_ico;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        itemImage = (ImageView) this.findViewById(R.id.item_image);
        itemName = (TextView) this.findViewById(R.id.item_name);
        badgeView = new JupiterBadgeView(this.getContext(), itemImage);
        itemImage.setLayoutParams(new FrameLayout.LayoutParams(PublicHelp
                                                                .dip2px(this.getContext(),
                                                                        DEFAULT_IMAGE_SIZE),
                                                                PublicHelp
                                                                        .dip2px(this.getContext(),
                                                                                DEFAULT_IMAGE_SIZE)));
        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs){
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.JupiterTextIco);

        try{
            if (typedArray.hasValue(R.styleable.JupiterTextIco_ico_title)){
                String titleText = typedArray.getString(R.styleable.JupiterTextIco_ico_title);
                this.itemName.setText(titleText);
            }

            if (typedArray.hasValue(R.styleable.JupiterTextIco_ico_image)){
                Drawable mainImage = typedArray.getDrawable(R.styleable.JupiterTextIco_ico_image);
                if (mainImage != null){
                    this.itemImage.setImageDrawable(mainImage);
                }
            }

            if (typedArray.hasValue(R.styleable.JupiterTextIco_ico_badge_image)){
                Drawable mainImage = typedArray.getDrawable(R.styleable.JupiterTextIco_ico_badge_image);
                if (mainImage != null){
                    this.badgeView.setBackgroundDrawable(mainImage);
                }
            }
        }finally{
            typedArray.recycle();
        }
    }

    private void buildTitle(String title) {
        // 设置图片描述文字
        if (title != null) {
            itemName.setVisibility(VISIBLE);
            itemName.setText(title);
        } else {
            itemName.setVisibility(GONE);
        }
    }

    private void buildImage(String image) {
        // 设置图片
        if (AssertValue.isNotNullAndNotEmpty(image)) {
            itemImage.setVisibility(VISIBLE);
            if (errorImage != -1){
                ImageLoaderUtil.getInstance(this.getContext()).displayImage(image, itemImage,errorImage);
            } else {
                ImageLoaderUtil.getInstance(this.getContext()).displayImage(image, itemImage);
            }
        } else {
            itemImage.setVisibility(GONE);
        }
    }

    private void buildBadge(int cornerImage) {
        if (cornerImage != 0) {
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
            badgeView.setBackgroundResource(cornerImage);
            badgeView.setBadgeSize(20, 20);
        }

    }

    public void edit(boolean edit) {
        if (edit) {
            badgeView.show();
        } else {
            badgeView.hide();
        }
    }

    public void showCorner() {
        badgeView.show();
    }

    public void hideCorner() {
        badgeView.hide();
    }

    public ImageView getItemImage() {
        return itemImage;
    }

    public void setItemImage(ImageView itemImage) {
        this.itemImage = itemImage;
    }

    public TextView getItemName() {
        return itemName;
    }

    public void setItemName(TextView itemName) {
        this.itemName = itemName;
    }

    public JupiterBadgeView getBadgeView() {
        return badgeView;
    }

    public void setBadgeView(JupiterBadgeView badgeView) {
        this.badgeView = badgeView;
    }

    public JupiterTextIco setTitle(String title) {
        buildTitle(title);
        return this;
    }

    public JupiterTextIco setOval(boolean oval) {
//        buildImage();
        return this;
    }

    public JupiterTextIco setImage(String image) {
        buildImage(image);
        return this;
    }

    public JupiterTextIco setCornerImage(int cornerImage) {
        buildBadge(cornerImage);
        return this;
    }

    public int getErrorImage() {
        return errorImage;
    }

    public JupiterTextIco setErrorImage(int errorImage) {
        this.errorImage = errorImage;
        return this;
    }
}
