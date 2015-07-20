package com.yun9.wservice.view.order;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yun9.jupiter.util.PublicHelp;
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

    private void addLine(final LinearLayout preItem,final LinearLayout currItem) {
        final int lineHeith = 4;
        final int circleWidth = PublicHelp.dip2px(this.mContext,30);
        currItem.postDelayed(new Runnable() {
            public void run() {
                View line = new View(mContext);
                int[] loc = new int[2];
                currItem.getLocationOnScreen(loc);
                int radio = (int)(currItem.getX() - preItem.getX() - circleWidth)/2;
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        radio * 2,lineHeith
                );
                layoutParams.setMargins((int) (currItem.getX() - radio),
                        (int) currItem.getY()+circleWidth/2,
                        0,
                        0);
                line.setLayoutParams(layoutParams);
                line.setBackgroundColor(getResources().getColor(R.color.groupbackground));
                OrderDetailProcessWidget.this.addView(line);
            }
        }, 100);
    }
}
