package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterBadgeView;

/**
 * Created by huangbinglong on 15/6/2.
 */
public class JupiterTextIco extends JupiterRelativeLayout {

    private ImageView itemImage;
    private TextView itemName;
    private LinearLayout container;
    private JupiterBadgeView badgeView;

    private String title;  // 图片标题

    private boolean oval;  //是否将图片弄成椭圆形状

    private String image;   // 图片ID

    private int cornerImage; // 用户指定角标图片

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
        buildImage();
        buildTitle();
        buildBadge();
    }

    private void buildTitle() {
        // 设置图片描述文字
        if (AssertValue.isNotNullAndNotEmpty(this.title)) {
            itemName.setVisibility(VISIBLE);
            itemName.setText(this.title);
        } else {
            itemName.setVisibility(GONE);
        }
    }

    private void buildImage() {
        // 设置图片
        if (AssertValue.isNotNullAndNotEmpty(this.image)) {
            itemImage.setVisibility(VISIBLE);
            ImageLoader.getInstance().displayImage(this.image, itemImage);
        } else {
            itemImage.setVisibility(GONE);
        }
    }

    private void buildBadge() {
        if (this.cornerImage != 0) {
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
            badgeView.setBackgroundResource(this.cornerImage);
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

    public LinearLayout getContainer() {
        return container;
    }

    public void setContainer(LinearLayout container) {
        this.container = container;
    }

    public JupiterBadgeView getBadgeView() {
        return badgeView;
    }

    public void setBadgeView(JupiterBadgeView badgeView) {
        this.badgeView = badgeView;
    }

    public String getTitle() {
        return title;
    }

    public JupiterTextIco setTitle(String title) {
        this.title = title;
        buildTitle();
        return this;
    }

    public boolean isOval() {
        return oval;
    }

    public JupiterTextIco setOval(boolean oval) {
        this.oval = oval;
        buildImage();
        return this;
    }

    public String getImage() {
        return image;
    }

    public JupiterTextIco setImage(String image) {
        this.image = image;
        buildImage();
        return this;
    }

    public int getCornerImage() {
        return cornerImage;
    }

    public JupiterTextIco setCornerImage(int cornerImage) {
        this.cornerImage = cornerImage;
        buildBadge();
        return this;
    }
}
