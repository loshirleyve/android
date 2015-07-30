package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.cache.UserDataCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.model.CacheUser;
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
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderCartInfo;
import com.yun9.wservice.model.wrapper.OrderCartInfoWrapper;

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

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private OrderCartCommand command;

    private List<OrderCartInfo> orderList;

    public static void start(Activity activity,OrderCartCommand command) {
        Intent intent = new Intent(activity, OrderCartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OrderCartCommand.PARAM_COMMAND,command);
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
    }

    private void reload() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryOrderViewService");
        resource.param("buyinstid",sessionManager.getInst().getId());
        resource.param("purchase",sessionManager.getUser().getId());
        resource.param("createby",sessionManager.getUser().getId());
        if (ClientProxyCache.getInstance().isProxy()){
            CacheClientProxy clientProxy = ClientProxyCache.getInstance().getProxy();
            resource.param("proxyinstid",sessionManager.getInst().getId());
            resource.param("proxyperson",sessionManager.getUser().getId());
            resource.param("purchase",clientProxy.getUserId());
            resource.param("buyinstid",clientProxy.getInstId());
        }
        resource.param("orderProductViews", command.getOrderProductViews());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                OrderCartInfoWrapper wrapper = (OrderCartInfoWrapper) response.getPayload();
                orderList = wrapper.getOrderViews();
                checkTitle();
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

    private void checkTitle() {
        if (ClientProxyCache.getInstance().isProxy()){
            CacheInst inst = InstCache.getInstance().getInst(ClientProxyCache.getInstance().getProxy().getInstId());
            if (inst != null){
                titleBarLayout.getTitleSutitleTv().setText("正在为"+inst.getInstname()+"代理购买");
            }
        } else {
            titleBarLayout.getTitleSutitleTv().setVisibility(View.GONE);
        }
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
