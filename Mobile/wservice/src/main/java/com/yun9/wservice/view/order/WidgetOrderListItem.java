package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by rxy on 15/8/20.
 */
public class WidgetOrderListItem extends JupiterRelativeLayout {

    private TextView orderSn;
    private TextView orderState;
    private ImageView orderImage;
    private TextView orderInstname;
    private TextView productName;
    private TextView orderDesc;
    private TextView orderPrice;
    private TextView orderDate;
    private JupiterListView orderWork;


    public WidgetOrderListItem(Context context) {
        super(context);
    }

    public WidgetOrderListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidgetOrderListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        orderSn = (TextView) findViewById(R.id.order_sn_tv);
        orderState = (TextView) findViewById(R.id.order_state_tv);
        orderImage = (ImageView) findViewById(R.id.order_img);
        orderInstname = (TextView) findViewById(R.id.order_inst_tv);
        productName= (TextView) findViewById(R.id.product_name_tv);
        orderDesc = (TextView) findViewById(R.id.order_desc_tv);
        orderPrice = (TextView) findViewById(R.id.order_price_tv);
        orderDate = (TextView) findViewById(R.id.order_date_tv);
        orderWork = (JupiterListView) findViewById(R.id.order_work);
    }

    public TextView getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(TextView orderSn) {
        this.orderSn = orderSn;
    }

    public TextView getOrderState() {
        return orderState;
    }

    public void setOrderState(TextView orderState) {
        this.orderState = orderState;
    }

    public ImageView getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(ImageView orderImage) {
        this.orderImage = orderImage;
    }

    public TextView getOrderInstname() {
        return orderInstname;
    }

    public void setOrderInstname(TextView orderInstname) {
        this.orderInstname = orderInstname;
    }


    public TextView getProductName() {
        return productName;
    }

    public void setProductName(TextView productName) {
        this.productName = productName;
    }

    public TextView getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(TextView orderDesc) {
        this.orderDesc = orderDesc;
    }

    public TextView getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(TextView orderPrice) {
        this.orderPrice = orderPrice;
    }

    public TextView getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(TextView orderDate) {
        this.orderDate = orderDate;
    }

    public JupiterListView getOrderWork() {
        return orderWork;
    }

    public void setOrderWork(JupiterListView orderWork) {
        this.orderWork = orderWork;
    }
}
