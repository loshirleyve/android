package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.model.CtrlCode;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderBaseInfo;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderItemWidget extends JupiterRelativeLayout{

    private TextView productNameTV;
    private TextView productPriceTV;
    private TextView orderSnTV;
    private TextView orderTimeTV;
    private ImageView productImgeIV;
    private TextView orderStateTV;

    public OrderItemWidget(Context context) {
        super(context);
    }

    public OrderItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void builWithData(OrderBaseInfo order) {
        productNameTV.setText(order.getProductname());
        ImageLoaderUtil.getInstance(this.getContext()).displayImage(order.getProductimgid(), productImgeIV);
        productPriceTV.setText(order.getOrderamount() + "元");
        orderSnTV.setText(order.getOrdersn());

        orderStateTV.setText(
                CtrlCodeCache.getInstance()
                        .getCtrlcodeName(CtrlCodeDefNo.ORDER_STATE,order.getState()));
        orderTimeTV.setText(DateFormatUtil.format(order.getCreatedate(),"yyyy年MM月dd日"));
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_list_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productNameTV = (TextView) this.findViewById(R.id.product_name_tv);
        productPriceTV = (TextView) this.findViewById(R.id.product_price_tv);
        orderSnTV = (TextView) this.findViewById(R.id.order_sn_tv);
        orderTimeTV = (TextView) this.findViewById(R.id.order_time_tv);
        productImgeIV = (ImageView) this.findViewById(R.id.product_image_iv);
        orderStateTV = (TextView) this.findViewById(R.id.order_state_tv);
    }
}
