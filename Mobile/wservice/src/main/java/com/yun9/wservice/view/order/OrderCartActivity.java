package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.CacheClientProxy;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.model.OrderCartInfo;
import com.yun9.wservice.model.wrapper.OrderCartInfoWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderCartActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.order_list)
    private ListView orderLV;

    @ViewInject(id=R.id.order_fee_tv)
    private TextView orderFeeTV;
    @ViewInject(id=R.id.pay_now_ll)
    private LinearLayout payNowLL;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private OrderCartCommand command;

    private OrderCartInfoWrapper orderCartInfoWrapper;

    private List<OrderCartInfo> orderList;

    public static void start(Activity activity,OrderCartCommand command) {
        Intent intent = new Intent(activity, OrderCartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderCartCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrderCartCommand) getIntent().getSerializableExtra(OrderCartCommand.PARAM_COMMAND);
        titleBarLayout.getTitleSutitleTv().setVisibility(View.GONE);
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
        payNowLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrderAndPay();
            }
        });
    }


    private void submitOrderAndPay() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        SessionManager sessionManager = JupiterApplication.getBeanManager().get(SessionManager.class);
        ResourceFactory resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
        Resource resource = resourceFactory.create("UpdateOrderByBuyService");
        resource.param("orderid",orderCartInfoWrapper.getId());
        resource.param("userid",sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showOrder(orderCartInfoWrapper.getId());
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    private void showOrder(String orderId) {
        OrderDetailActivity.start(this, orderId);
        this.finish();
    }


    private void reload() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("SelectProductsService");
        resource.param("buyInstId",sessionManager.getInst().getId());
        resource.param("buyUserId",sessionManager.getUser().getId());
        resource.param("buyInstId",sessionManager.getInst().getId());
        resource.param("createBy",sessionManager.getUser().getId());
        if (ClientProxyCache.getInstance().isProxy()){
            CacheClientProxy clientProxy = ClientProxyCache.getInstance().getProxy();
            resource.param("proxyInstId",sessionManager.getInst().getId());
            resource.param("proxyUserId",sessionManager.getUser().getId());
            resource.param("buyUserId",clientProxy.getUserId());
            resource.param("buyInstId",clientProxy.getInstId());
            resource.param("saleUserId",sessionManager.getUser().getId());
        }
        resource.param("items", toItems(command.getOrderProductViews()));
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                orderCartInfoWrapper = (OrderCartInfoWrapper) response.getPayload();
                orderFeeTV.setText(orderCartInfoWrapper.getFactamount() + "元");
                loadProducts(orderCartInfoWrapper.getId());
                checkTitle();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
                orderCartInfoWrapper = null;
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                registerDialog.dismiss();
            }
        });
    }

    private void loadProducts(String orderId) {
        final ProgressDialog registerDialog =
                ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        final Resource resource = resourceFactory.create("QueryOrderProductsByOrderidService");
        resource.param("orderid",orderId);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                orderList = (List<OrderCartInfo>) response.getPayload();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
                orderList = null;
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                registerDialog.dismiss();
            }
        });
    }

    private List<Map<String,String>> toItems(
            List<OrderCartCommand.OrderProductView> orderProductViews) {
        List<Map<String,String>> items = new ArrayList<>();
        for (OrderCartCommand.OrderProductView orderProductView : orderProductViews) {
            Map<String,String> map = new HashMap<>();
            map.put("productid",orderProductView.getProductid());
            map.put("classifyid",orderProductView.getClassifyid());
            items.add(map);
        }
        return items;
    }

    private void checkTitle() {
        if (ClientProxyCache.getInstance().isProxy()){
            CacheInst inst = InstCache.getInstance().getInst(ClientProxyCache.getInstance().getProxy().getInstId());
            if (inst != null){
                titleBarLayout.getTitleSutitleTv().setVisibility(View.VISIBLE);
                titleBarLayout.getTitleSutitleTv().setText("正在为"+inst.getInstname()+"代理购买");
            } else {
                titleBarLayout.getTitleSutitleTv().setText("警告：无法获取代理机构信息！！");
            }
        } else {
            titleBarLayout.getTitleSutitleTv().setVisibility(View.GONE);
        }
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (orderList != null) {
                return 1;
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
                widget.buildWithData(orderCartInfoWrapper,orderList);
                convertView = widget;
            } else {
                widget = (OrderInfoWidget) convertView;
            }
            return convertView;
        }
    };
}
