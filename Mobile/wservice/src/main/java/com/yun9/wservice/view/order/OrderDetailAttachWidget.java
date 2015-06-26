package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailAttachWidget extends JupiterRelativeLayout{

    private JupiterRowStyleTitleLayout titleLayout;

    public OrderDetailAttachWidget(Context context) {
        super(context);
    }

    public OrderDetailAttachWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailAttachWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order order) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_attach;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        titleLayout = (JupiterRowStyleTitleLayout) this.findViewById(R.id.title_layout);
        buildView();
    }

    private void buildView() {
        titleLayout.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.purple_font));
//        titleLayout.getHotNitoceTV().getPaint().setFakeBoldText(true);
        titleLayout.getHotNitoceTV().setText(R.string.checkout_attach);
        titleLayout.getTitleTV().setTextSize(14);
        titleLayout.getTitleTV().setTextColor(getResources().getColor(R.color.purple_font));

    }
}
