package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderCartActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.order_list)
    private ListView orderLV;

    private OrderCartCommand command;

    private List<Order> orderList;

    private boolean isProxy;

    public static void start(Activity activity,OrderCartCommand command) {
        Intent intent = new Intent(activity, OrderCartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command",command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
        reload();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_cart;
    }

    private void buildView() {
        orderLV.setAdapter(adapter);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(JupiterCommand.RESULT_CODE_CANCEL);
                OrderCartActivity.this.finish();
            }
        });
    }

    private void reload() {
        orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (orderList != null) {
                return orderList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderInfoWidget widget;
            if (convertView == null) {
                widget = new OrderInfoWidget(OrderCartActivity.this);
                widget.buildWithData(orderList.get(position));
                convertView = widget;
            } else {
                widget = (OrderInfoWidget) convertView;
            }
            return convertView;
        }
    };
}
