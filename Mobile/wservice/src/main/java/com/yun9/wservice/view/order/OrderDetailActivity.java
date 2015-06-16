package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderDetailActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    private String orderId;

    public static void start(Activity activity,String orderId) {
        Intent intent = new Intent(activity,OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid",orderId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra("orderid");
        this.buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    private void buildView() {
        titleBarLayout.getTitleTv().setText(R.string.order_detail);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.this.finish();
            }
        });
    }
}
