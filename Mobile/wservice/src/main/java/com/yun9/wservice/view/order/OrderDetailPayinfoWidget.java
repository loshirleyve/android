package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.State;
import com.yun9.wservice.view.payment.PaymentOrderActivity;
import com.yun9.wservice.view.payment.PaymentOrderCommand;
import com.yun9.wservice.view.payment.PaymentResultActivity;
import com.yun9.wservice.view.payment.RechargeResultActivity;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailPayinfoWidget extends JupiterRelativeLayout{

    private JupiterRowStyleSutitleLayout sutitleLayout;

    private Order order;

    public OrderDetailPayinfoWidget(Context context) {
        super(context);
    }

    public OrderDetailPayinfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailPayinfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_payinfo;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.subtitle_layout);
        buildView();
    }

    public void buildWithData(Order order) {
        this.order = order;
        if (order.getOrder().getPaystate() > 0) {
            sutitleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.purple_font));
            sutitleLayout.getHotNitoceTV().setText("查看付款详情");
            sutitleLayout.getHotNitoceTV().getPaint().setFakeBoldText(false);
            sutitleLayout.getTitleTV().setTextColor(getResources().getColor(R.color.black));
            sutitleLayout.getTitleTV().setText(R.string.already_pay);
            sutitleLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentResultActivity.start(OrderDetailPayinfoWidget.this.mContext);
                }
            });
        }
    }

    private void buildView() {
        sutitleLayout.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
        sutitleLayout.getHotNitoceTV().setVisibility(VISIBLE);
        sutitleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
        sutitleLayout.getHotNitoceTV().getPaint().setFakeBoldText(true);
        sutitleLayout.getHotNitoceTV().setText(R.string.payment_now);
        sutitleLayout.getTitleTV().setTextSize(14);
        sutitleLayout.getSutitleTv().setTextColor(getResources().getColor(R.color.purple_font));
        sutitleLayout.getTitleTV().setTextColor(getResources().getColor(R.color.red));
            sutitleLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (order != null) {
                        PaymentOrderCommand command = new PaymentOrderCommand();
                        command.setSource(PaymentOrderCommand.SOURCE_ORDER);
                        command.setSourceValue(order.getOrder().getOrderid());
                        command.setInstId(order.getOrder().getProvideinstid());
                        PaymentOrderActivity.start(OrderDetailPayinfoWidget.this.getContext(), command);
                    }
                }
            });
    }

}
