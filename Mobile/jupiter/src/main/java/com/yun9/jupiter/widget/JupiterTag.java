package com.yun9.jupiter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterBadgeView;

/**
 * Created by huangbinglong on 15/6/2.
 */
public class JupiterTag extends JupiterEditableView{

    private TextView itemName;

    public JupiterTag(Context context) {
        super(context);
    }

    public JupiterTag(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterTag(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_tag;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        itemName = (TextView) this.findViewById(R.id.item_name);
    }


    private void buildTitle(String title) {
        // 设置图片描述文字
        if (AssertValue.isNotNullAndNotEmpty(title)) {
            itemName.setVisibility(VISIBLE);
            itemName.setText(title);
        } else {
            itemName.setVisibility(GONE);
        }
    }

    public void edit(boolean edit) {

    }

    public TextView getItemName() {
        return itemName;
    }

    public void setItemName(TextView itemName) {
        this.itemName = itemName;
    }

    public JupiterTag setTitle(String title) {
        buildTitle(title);
        return this;
    }

}
