package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderBaseInfo;
import com.yun9.wservice.model.wrapper.OrderBaseInfoWrapper;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderListActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id=R.id.order_list_ptr)
    private PagingListView orderLV;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String state;

    private List<OrderBaseInfo> orderList;

    private String pullRowid = null;

    private String pushRowid = null;

    public static void start(Activity activity,String state,String stateName) {
        Intent intent = new Intent(activity, OrderListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        bundle.putString("stateName", stateName);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getIntent().getStringExtra("state");
        String stateName = getIntent().getStringExtra("stateName");
        if (AssertValue.isNotNullAndNotEmpty(stateName)) {
            titleBarLayout.getTitleTv().setText(stateName+"订单");
        }
        orderList = new ArrayList<>();
        this.buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_list;
    }

    private void buildView() {
        orderLV.setAdapter(adapter);
        orderLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDetailActivity.start(OrderListActivity.this, orderList.get(position).getOrderid());
            }
        });
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderListActivity.this.finish();
            }
        });
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                orderLV.setHasMoreItems(true);
                pullRowid = null;
                orderList.clear();
                refresh(pullRowid, Page.PAGE_DIR_PULL);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        orderLV.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(pushRowid)){
                    refresh(pushRowid,Page.PAGE_DIR_PUSH);
                } else {
                    orderLV.onFinishLoading(true);
                }
            }
        });
        autoRefresh();
    }

    private void refresh(final String rowid, final String dir) {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshOrder(rowid,dir);
            }
        }, 100);
    }

    private void autoRefresh(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private void refreshOrder(String rowid, final String dir) {
        final Resource resource = resourceFactory.create("QueryOrdersService");
        resource.param("userid", sessionManager.getUser().getId());
        if (AssertValue.isNotNullAndNotEmpty(state)){
            resource.param("states",new String[]{state});
        }
        resource.page().setRowid(rowid).setDir(dir);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                OrderBaseInfoWrapper wrapper = (OrderBaseInfoWrapper) response.getPayload();
                List<OrderBaseInfo> orderBaseInfos = wrapper.getOrderList();
                if (orderBaseInfos != null && orderBaseInfos.size() > 0){
                    if (Page.PAGE_DIR_PULL.equals(dir)){
                        pullRowid = orderBaseInfos.get(0).getOrderid();
                        orderList.addAll(0,orderBaseInfos);
                        if (!AssertValue.isNotNullAndNotEmpty(pushRowid)){
                            pushRowid = orderBaseInfos.get(orderBaseInfos.size() - 1).getOrderid();
                        }
                        if (orderBaseInfos.size() < Integer.valueOf(resource.page().getSize())){
                            orderLV.setHasMoreItems(false);
                        }
                    } else {
                        pushRowid = orderBaseInfos.get(orderBaseInfos.size() - 1).getOrderid();
                        orderList.addAll(orderBaseInfos);
                    }
                } else if (Page.PAGE_DIR_PULL.equals(dir)) {
                    showToast("没有新数据");
                } else if (Page.PAGE_DIR_PUSH.equals(dir)) {
                    orderLV.setHasMoreItems(false);
                    showToast(R.string.app_no_more_data);
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                ptrClassicFrameLayout.refreshComplete();
                orderLV.onFinishLoading(true);
            }
        });

    }

    private JupiterAdapter  adapter = new JupiterAdapter() {
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
            OrderItemWidget itemWidget;
            if (convertView == null) {
                itemWidget = new OrderItemWidget(OrderListActivity.this);
                convertView = itemWidget;
            } else {
                itemWidget = (OrderItemWidget) convertView;
            }
            itemWidget.builWithData(orderList.get(position));
            return convertView;
        }
    };
}
