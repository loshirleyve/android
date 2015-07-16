package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.State;
import com.yun9.wservice.model.WorkOrderComment;

import org.w3c.dom.Text;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailWorkOrderWidget extends JupiterRelativeLayout{

    private TextView workOrderIdTV;
    private TextView workOrderNameTV;
    private TextView workOrderStateTV;
    private TextView checkoutWorkOrderCommentTV;

    private String orderId;

    private Order.OrderWorkOrder workOrder;

    public OrderDetailWorkOrderWidget(Context context) {
        super(context);
    }

    public OrderDetailWorkOrderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailWorkOrderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(String orderId,Order.OrderWorkOrder workOrder) {
        this.orderId = orderId;
        this.workOrder = workOrder;
        workOrderNameTV.setText(workOrder.getOrderworkname());
        workOrderIdTV.setText("工单号 "+workOrder.getOrderworkid());
        workOrderStateTV.setText(CtrlCodeCache.getInstance()
                .getCtrlcodeName(CtrlCodeDefNo.WORK_STATE, workOrder.getOrderworkstate()));
        // 没有完成不能评论
        if (!State.WorkOrder.COMPLETE.equals(workOrder.getOrderworkstate())) {
            checkoutWorkOrderCommentTV.setVisibility(GONE);
        } else {
            checkoutComment();
        }
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
        checkoutWorkOrderCommentTV = (TextView) this.findViewById(R.id.checkout_work_order_comment_tv);
        buildView();
    }

    private void buildView() {
    }

    private void checkoutComment() {
        ResourceFactory resourceFactory = JupiterApplication.getBeanManager()
                                                                .get(ResourceFactory.class);
        Resource resource = resourceFactory.create("QueryWorkCommentsByWorkOrderIdService");
        resource.param("workorderid", workOrder.getOrderworkid());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                WorkOrderComment comment = (WorkOrderComment) response.getPayload();
                if (comment != null
                        && AssertValue.isNotNullAndNotEmpty(comment.getId())) {
                    checkoutWorkOrderCommentTV.setText("查看评论");
                    checkoutWorkOrderCommentTV.setOnClickListener(showComment);
                } else {
                    checkoutWorkOrderCommentTV.setOnClickListener(commentWorkOrder);
                    checkoutWorkOrderCommentTV.setText("评论工单");
                }
            }

            @Override
            public void onFailure(Response response) {
                checkoutWorkOrderCommentTV.setVisibility(GONE);
            }

            @Override
            public void onFinally(Response response) {
            }
        });
    }

    private OnClickListener commentWorkOrder = new OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderCommentCommand commentCommand = new OrderCommentCommand();
            commentCommand.setOrderId(orderId).setWorkOrder(workOrder);
            OrderCommentActivity.start((Activity) OrderDetailWorkOrderWidget.this.getContext(),
                    commentCommand);
        }
    };

    private OnClickListener showComment = new OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderCommentCommand commentCommand = new OrderCommentCommand();
            commentCommand.setOrderId(orderId).setWorkOrder(workOrder);
            OrderCommentDetailActivity.start((Activity) OrderDetailWorkOrderWidget.this.getContext(),
                    commentCommand);
        }
    };
}
