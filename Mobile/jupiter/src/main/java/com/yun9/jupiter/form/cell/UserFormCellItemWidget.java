package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yun9.jupiter.R;
import com.yun9.jupiter.widget.JupiterRelativeLayout;

/**
 * Created by huangbinglong on 15/5/30.
 */
public class UserFormCellItemWidget extends JupiterRelativeLayout{

    private ImageView itemImage;
    private TextView itemName;
    private LinearLayout container;

    public UserFormCellItemWidget(Context context) {
        super(context);
    }

    public UserFormCellItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserFormCellItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_user_form_cell_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        itemImage = (ImageView) this.findViewById(R.id.item_image);
        itemName = (TextView) this.findViewById(R.id.item_name);
        container = (LinearLayout) this.findViewById(R.id.container);
    }

    public void buildWithData(String imageId,String name) {
        ImageLoader.getInstance().displayImage(imageId,itemImage);
        itemName.setText(name);
    }

    public LinearLayout getContainer() {
        return container;
    }
}
