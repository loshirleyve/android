package com.yun9.wservice.view.order;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangbinglong on 15/6/19.
 */
public class OrderDetailProcessWidget extends JupiterRelativeLayout{

    private LinearLayout processContainer;

    private RelativeLayout outterContainter;

    public OrderDetailProcessWidget(Context context) {
        super(context);
    }

    public OrderDetailProcessWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailProcessWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order order) {
        processContainer.removeAllViews();
        if (order == null || order.getOrderLogs() == null) {
            return;
        }
        List<Order.OrderLog> logs = order.getOrderLogs();
        LinearLayout preItem = null;
        LinearLayout currItem = null;
        boolean isMark = false;
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getOrderstate().equals(order.getOrder().getState())){
                currItem = this.addItem(logs.get(i),R.drawable.dingdang1);
                currItem.setTag(true);
                isMark = true;
            } else if (!isMark){
                currItem = this.addItem(logs.get(i),R.drawable.dingdang3);
                currItem.setTag(false);
            } else {
                currItem = this.addItem(logs.get(i),R.drawable.dingdang2);
                currItem.setTag(false);
            }

            if (preItem == null) {
                preItem = currItem;
            }
            if (currItem != preItem) {
                addLine(preItem,currItem);
                preItem = currItem;
            }
        }
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_process;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        processContainer = (LinearLayout) this.findViewById(R.id.process_container);
        outterContainter = (RelativeLayout) this.findViewById(R.id.outter_container);
    }

    private LinearLayout addItem(Order.OrderLog log,int realCircle) {
        OrderDetailProcessItemWidget itemWidget = new OrderDetailProcessItemWidget(this.getContext());
        itemWidget.buildWithData(log);
        itemWidget.getCircleIV().setImageResource(realCircle);
        LinearLayout container = itemWidget.getContainer();
        LinearLayout item = (LinearLayout) container.getChildAt(0);
        container.removeView(item);
        processContainer.addView(item);
        return item;
    }

    private void addLine(LinearLayout preItem,LinearLayout currItem) {
        final int lineHeith = 4;
        final ImageView preCircle = (ImageView) preItem.findViewById(R.id.circle_iv);
        ImageView currCircle = (ImageView) currItem.findViewById(R.id.circle_iv);
        final View line = new View(this.getContext());
        line.setBackgroundColor(this.getResources().getColor(R.color.groupbackground));
        preItem.addView(line);
        preItem.postDelayed(new Runnable() {
            public void run() {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, lineHeith);
                params.setMargins(preCircle.getWidth(),0, 0,0);
                line.setLayoutParams(params);
            }
        }, 10);
    }
}
