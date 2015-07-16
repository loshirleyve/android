package com.yun9.wservice.view.product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.view.order.OrderWorkOrderSubCommentWidget;
import com.yun9.wservice.widget.ShowCommentWidget;

/**
 * Created by rxy on 15/7/16.
 */
public class ProductCommentWidget extends JupiterRelativeLayout {

    private ShowCommentWidget showCommentWidget;

    private JupiterListView subCommentLv;

    public ProductCommentWidget(Context context) {
        super(context);
    }

    public ProductCommentWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductCommentWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_comment_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        showCommentWidget = (ShowCommentWidget) findViewById(R.id.show_comment_widget);
        subCommentLv = (JupiterListView) findViewById(R.id.sub_comment_lv);
    }

    public ShowCommentWidget getShowCommentWidget() {
        return showCommentWidget;
    }

    public JupiterListView getSubCommentLv() {
        return subCommentLv;
    }
}
