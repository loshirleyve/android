package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderBuyManagerInfo;
import com.yun9.wservice.view.payment.RechargeRecordListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 购买管理界面
 * Created by huangbinglong on 15/6/12.
 */
public class OrderManagerActivity extends JupiterFragmentActivity {

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.order_category_list)
    private ListView orderCategoryLV;

    @ViewInject(id = R.id.recharge_category_list)
    private ListView rechargeHistoryLV;

    @ViewInject(id = R.id.order_recharge)
    private OrderRechargeWidget orderRechargeWidget;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private OrderBuyManagerInfo buyManagerInfo;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, OrderManagerActivity.class);
        intent.putExtras(new Bundle());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.buildView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reLoadData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_manager;
    }

    private void buildView() {
        orderCategoryLV.setAdapter(orderAdapter);
        rechargeHistoryLV.setAdapter(rechargeAdapter);

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(JupiterCommand.RESULT_CODE_CANCEL);
                OrderManagerActivity.this.finish();
            }
        });
    }

    private void reLoadData() {
        Resource resource = resourceFactory.create("QueryBuyManagerInfoByUserIdService");
        resource.param("userid", sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                buyManagerInfo = (OrderBuyManagerInfo) response.getPayload();
                orderRechargeWidget.buildWithData(buyManagerInfo.getBalance());
            }

            @Override
            public void onFailure(Response response) {
                buyManagerInfo = null;
            }

            @Override
            public void onFinally(Response response) {
                orderAdapter.notifyDataSetChanged();
                rechargeAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 根据不同状态的订单打开订单列表
     *
     * @param orderGroup
     */
    private void openOrderListActivity(OrderBuyManagerInfo.OrderGroup orderGroup) {
        OrderListActivity.start(this,orderGroup.getState(),orderGroup.getStatename());
    }

    private void openRechargeHistoryActivity(OrderBuyManagerInfo.RechargeGroup rechargeGroup) {
        RechargeRecordListActivity.start(this,rechargeGroup.getState(),rechargeGroup.getStatename());
    }

    private JupiterAdapter rechargeAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (buyManagerInfo != null
                    && buyManagerInfo.getRecharegeGroups() != null) {
                return buyManagerInfo.getRecharegeGroups().size();
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
            JupiterRowStyleTitleLayout row;
            final OrderBuyManagerInfo.RechargeGroup rechargeGroup = buyManagerInfo
                                                                        .getRecharegeGroups()
                                                                            .get(position);
            if (convertView == null) {
                row = new JupiterRowStyleTitleLayout(OrderManagerActivity.this);
                row.getMainIV().setVisibility(View.GONE);
                row.getHotNitoceTV().setVisibility(View.VISIBLE);
                row.getHotNitoceTV().setTextColor(getResources().getColor(R.color.drak));
                row.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openRechargeHistoryActivity(rechargeGroup);
                    }
                });
                convertView = row;
            } else {
                row = (JupiterRowStyleTitleLayout) convertView;
            }
            row.getHotNitoceTV().setText(rechargeGroup.getCount() + "");
            row.getTitleTV().setText(rechargeGroup.getStatename());
            return convertView;
        }
    };

    private JupiterAdapter orderAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (buyManagerInfo != null
                    && buyManagerInfo.getOrderGroups() != null) {
                return buyManagerInfo.getOrderGroups().size();
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
            JupiterRowStyleTitleLayout row;
            final OrderBuyManagerInfo.OrderGroup orderGroup = buyManagerInfo.getOrderGroups().get(position);
            if (convertView == null) {
                row = new JupiterRowStyleTitleLayout(OrderManagerActivity.this);
                row.getMainIV().setVisibility(View.GONE);
                row.getHotNitoceTV().setVisibility(View.VISIBLE);
                row.getHotNitoceTV().setTextColor(getResources().getColor(R.color.drak));
                row.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openOrderListActivity(orderGroup);
                    }
                });
                convertView = row;
            } else {
                row = (JupiterRowStyleTitleLayout) convertView;
            }
            row.getHotNitoceTV().setText(orderGroup.getNums() + "");
            row.getTitleTV().setText(orderGroup.getStatename());
            return convertView;
        }
    };
}
