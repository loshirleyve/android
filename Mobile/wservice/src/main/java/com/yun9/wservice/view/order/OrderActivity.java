package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheCtrlcodeItem;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderGroup;
import com.yun9.wservice.model.OrderInfo;
import com.yun9.wservice.model.WorkorderDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.PtrUIHandlerHook;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Leon on 15/6/26.
 */
public class OrderActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;


    @ViewInject(id = R.id.category_segmented)
    private SegmentedGroup segmentedGroup;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrFrame;

    @ViewInject(id = R.id.order_List)
    private PagingListView orderLists;

    private OrderGroup currOrderGroup;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private List<OrderGroup> orderGroups = new ArrayList<>();
    private LinkedList<OrderInfo> orderinfos = new LinkedList<>();

    private boolean isFirstIn = true;// 第一次进入，刷新时自动触发第一个分组的点击事件

    @Override
    protected int getContentView() {
        return R.layout.activity_order;
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtras(new Bundle());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected void initViews() {
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        cleanCategory();
        refreshOrderGroup();
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                orderinfos.clear();
                orderGroups.clear();

                if (!isFirstIn) {
                    refreshOrderGroup();
                }
                isFirstIn = false;
                mPtrFrame.refreshComplete();

                if (AssertValue.isNotNullAndNotEmpty(orderinfos)) {
                    refreshOrderInfos(currOrderGroup, orderinfos.get(0).getId(), Page.PAGE_DIR_PULL);
                } else {
                    refreshOrderInfos(currOrderGroup, null, Page.PAGE_DIR_PULL);
                }

            }
        });

        mPtrFrame.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
                enableSegmentGroup(true);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {
                enableSegmentGroup(false);
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        orderLists.setAdapter(orderListViewAdapter);
        orderLists.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(orderinfos)) {
                    OrderInfo order = orderinfos.get(orderinfos.size() - 1);
                    refreshOrderInfos(currOrderGroup, order.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    orderLists.onFinishLoading(true);
                }
            }
        });

    }


    private void refreshOrderGroup() {
        Resource resource = resourceFactory.create("QueryOrderGroupsByUserId");
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("instid", sessionManager.getInst().getId());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                orderGroups = (List<OrderGroup>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(orderGroups)) {
                    for (OrderGroup orderGroup : orderGroups) {
                        addOrUpdateCategory(orderGroup);
                    }

                    //触发第一个分类点击
                    if (segmentedGroup.getChildCount() > 0 && isFirstIn) {
                        segmentedGroup.getChildAt(0).performClick();
                    }
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrderActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                orderLists.onFinishLoading(true);
                mPtrFrame.refreshComplete();
            }
        });
    }


    private void refreshOrderInfos(final OrderGroup orderGroup, final String rowid, final String dir) {
        if (!AssertValue.isNotNull(orderGroup)) {
            mPtrFrame.refreshComplete();
            return;
        }

        Resource resource = resourceFactory.create("QueryOrdersByState");
        resource.param("state", orderGroup.getState());
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("userid", sessionManager.getUser().getId());
        resource.page().setRowid(rowid).setDir(dir);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<OrderInfo> orderInfos = (List<OrderInfo>) response.getPayload();

                if (AssertValue.isNotNullAndNotEmpty(orderInfos) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = orderInfos.size(); i > 0; i--) {
                        OrderInfo orderInfo = orderInfos.get(i - 1);
                        orderinfos.addFirst(orderInfo);
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(orderInfos) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (OrderInfo orderInfo : orderInfos) {
                        orderinfos.addLast(orderInfo);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(orderInfos) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    orderLists.onFinishLoading(false);
                }
                orderListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrderActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                enableSegmentGroup(true);
            }

            @Override
            public void onFinally(Response response) {
                mPtrFrame.refreshComplete();
                orderLists.onFinishLoading(true);
            }
        });
    }

    private void enableSegmentGroup(boolean isEnable) {
        int count = segmentedGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = segmentedGroup.getChildAt(i);
            if (view instanceof RadioButton){
                view.setEnabled(isEnable);
            }
        }
    }


    private void addCategory(OrderGroup orderGroup) {
        RadioButton radioButton = (RadioButton) this.getLayoutInflater().inflate(R.layout.radio_button_item, null);
        String orderGroupName = orderGroup.getStatename();
        if (orderGroup.getNums() > 0)
            orderGroupName += "(" + orderGroup.getNums() + ")";
        radioButton.setText(orderGroupName);
        radioButton.setTag(orderGroup);
        radioButton.setOnClickListener(onCategoryClickListener);
        segmentedGroup.addView(radioButton);
        segmentedGroup.updateBackground();
    }

    private void addOrUpdateCategory(OrderGroup orderGroup) {
        View view = segmentedGroup.findViewWithTag(orderGroup);
        if (view != null) {
            RadioButton radioButton = (RadioButton) view;
            String orderGroupName = orderGroup.getStatename();
            if (orderGroup.getNums() > 0)
                orderGroupName += "(" + orderGroup.getNums() + ")";
            radioButton.setText(orderGroupName);
        } else {
            addCategory(orderGroup);
        }
    }

    private void cleanCategory() {
        segmentedGroup.removeAllViews();
    }

    private View.OnClickListener onCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrderGroup orderGroup = (OrderGroup) v.getTag();
            orderinfos.clear();
            orderListViewAdapter.notifyDataSetChanged();
            if (AssertValue.isNotNull(orderGroup)) {
                currOrderGroup = orderGroup;
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.autoRefresh();
                    }
                }, 100);
            }
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private JupiterAdapter orderListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(orderinfos)) {
                return orderinfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return orderinfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WidgetOrderListItem widgetOrderListItem = null;
            final OrderInfo orderInfo = orderinfos.get(position);

            if (AssertValue.isNotNull(convertView)) {
                widgetOrderListItem = (WidgetOrderListItem) convertView;
            } else {
                widgetOrderListItem = new WidgetOrderListItem(OrderActivity.this);
            }
            widgetOrderListItem.getOrderSn().setText(orderInfo.getOrdersn());

            CacheCtrlcodeItem code = CtrlCodeCache.getInstance().getCtrlCodeItem("orderstatetype", orderInfo.getState());
            if (AssertValue.isNotNull(code))
                widgetOrderListItem.getOrderState().setText(code.getName());

            ImageLoaderUtil.getInstance(OrderActivity.this).displayImage(orderInfo.getImgid(), widgetOrderListItem.getOrderImage());

            CacheInst inst = InstCache.getInstance().getInst(orderInfo.getInstid());
            if (AssertValue.isNotNull(inst))
                widgetOrderListItem.getOrderInstname().setText(inst.getInstname());
            widgetOrderListItem.getProductName().setText(orderInfo.getName());
            widgetOrderListItem.getOrderDesc().setText(orderInfo.getIntroduce());
            widgetOrderListItem.getOrderPrice().setText(orderInfo.getFactamount() + "元");
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String date = format.format(new Date(orderInfo.getCreatedate()));
            widgetOrderListItem.getOrderDate().setText(date);
            widgetOrderListItem.getOrderWork().setAdapter(new OrderListSubItemAdapter(orderInfo.getWorkorders(), OrderActivity.this));
            widgetOrderListItem.setTag(orderInfo.getId());
            widgetOrderListItem.setOnClickListener(onOrderItemClickListener);
            widgetOrderListItem.getOrderWork().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WorkorderDto workorderDto = (WorkorderDto) view.getTag();
                    WorkOrderDetailActivity.start(view.getContext(),
                            new WorkOrderCommand().setSource("so").setOrderid(orderInfo.getId())
                                    .setWorkorderno(workorderDto.getNo()));
                }
            });
            return widgetOrderListItem;
        }
    };


    private View.OnClickListener onOrderItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String orderid = (String) v.getTag();
            OrderDetailActivity.start(OrderActivity.this, orderid);
        }
    };


}