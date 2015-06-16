package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

import org.w3c.dom.Text;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailWorkOrderWidget extends JupiterRelativeLayout{

    private TextView workOrderIdTV;
    private TextView workOrderNameTV;
    private TextView workOrderStateTV;
    private TextView commentWorkOrderTV;
    private TextView checkoutWorkOrderCommentTV;
    private TextView appendWorkOrderCommentTV;

    public OrderDetailWorkOrderWidget(Context context) {
        super(context);
    }

    public OrderDetailWorkOrderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailWorkOrderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData() {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_work_order;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        workOrderIdTV = (TextView) this.findViewById(R.id.work_order_id_tv);
        workOrderNameTV = (TextView) this.findViewById(R.id.work_order_name_tv);
        workOrderStateTV = (TextView) this.findViewById(R.id.work_order_state_tv);
        commentWorkOrderTV = (TextView) this.findViewById(R.id.comment_work_order_tv);
        checkoutWorkOrderCommentTV = (TextView) this.findViewById(R.id.checkout_work_order_comment_tv);
        appendWorkOrderCommentTV = (TextView) this.findViewById(R.id.append_comment_work_order_tv);
    }
}
