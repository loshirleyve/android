package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderGroup;
import com.yun9.wservice.model.OrderInfo;
import com.yun9.wservice.model.WorkorderDto;
import com.yun9.wservice.view.inst.SelectInstCommand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
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
        refreshOrderGroup();
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                if (!AssertValue.isNotNullAndNotEmpty(orderGroups)) {
                    refreshOrderGroup();
                    mPtrFrame.refreshComplete();
                } else {
                    if (AssertValue.isNotNullAndNotEmpty(orderinfos)) {
                        refreshOrderInfos(currOrderGroup, orderinfos.get(0).getOrderid(), Page.PAGE_DIR_PULL);
                    } else {
                        orderinfos.clear();
                        orderLists.setHasMoreItems(true);
                        refreshOrderInfos(currOrderGroup, null, Page.PAGE_DIR_PULL);
                    }
                }
            }
        });

        orderLists.setAdapter(orderListViewAdapter);
        orderLists.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(orderinfos)) {
                    OrderInfo order = orderinfos.get(orderinfos.size() - 1);
                    refreshOrderInfos(currOrderGroup, order.getOrderid(), Page.PAGE_DIR_PUSH);
                } else {
                    orderLists.onFinishLoading(true);
                }
            }
        });

    }


    private void refreshOrderGroup() {
        Resource resource = resourceFactory.create("QueryOrderGroupsByUserId");
        resource.param("userid", sessionManager.getUser().getId());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                orderGroups = (List<OrderGroup>) response.getPayload();
                cleanCategory();
                if (AssertValue.isNotNullAndNotEmpty(orderGroups)) {
                    for (OrderGroup orderGroup : orderGroups) {
                        addCategory(orderGroup);
                    }

                    //触发第一个分类点击
                    if (segmentedGroup.getChildCount() > 0) {
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
            }

            @Override
            public void onFinally(Response response) {
                mPtrFrame.refreshComplete();
                orderLists.onFinishLoading(true);
            }
        });
    }


    private void addCategory(OrderGroup orderGroup) {
        RadioButton radioButton = (RadioButton) this.getLayoutInflater().inflate(R.layout.radio_button_item, null);
        radioButton.setText(orderGroup.getStatename());
        radioButton.setTag(orderGroup);
        radioButton.setOnClickListener(onCategoryClickListener);
        segmentedGroup.addView(radioButton);
        segmentedGroup.updateBackground();
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
            OrderInfo orderInfo = orderinfos.get(position);

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

            widgetOrderListItem.getOrderDesc().setText(orderInfo.getIntroduce());
            widgetOrderListItem.getOrderPrice().setText(orderInfo.getOrderamount() + "元");
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            String date = format.format(new Date(orderInfo.getCreatedate()));
            widgetOrderListItem.getOrderDate().setText(date);
            widgetOrderListItem.getOrderWork().setAdapter(new Subadapter(orderInfo.getWorkorders()));
            widgetOrderListItem.setTag(orderInfo.getOrderid());
            widgetOrderListItem.setOnClickListener(onOrderItemClickListener);
            return widgetOrderListItem;
        }
    };


    private View.OnClickListener onOrderItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String orderid=(String)v.getTag();
            OrderDetailActivity.start(OrderActivity.this,orderid);
        }
    };



    private class Subadapter extends JupiterAdapter {

        private List<WorkorderDto> workorderDtos;

        public Subadapter(List<WorkorderDto> workorderDtos) {
            this.workorderDtos = workorderDtos;
        }

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(workorderDtos)) {
                return workorderDtos.size();
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
            WidgetOrderListSubItem widgetOrderListSubItem = null;
            WorkorderDto workorderDto =workorderDtos.get(position);
            if (convertView == null) {
                widgetOrderListSubItem = new WidgetOrderListSubItem(OrderActivity.this);
                convertView = widgetOrderListSubItem;
            } else {
                widgetOrderListSubItem = (WidgetOrderListSubItem) convertView;
            }
            widgetOrderListSubItem.getWorkorderitemname().setText(workorderDto.getInserviceName());
            widgetOrderListSubItem.getWorkorderitemdescr().setText(workorderDto.getDescr());
            widgetOrderListSubItem.getWorkorderitemnums().setText(workorderDto.getCompleteNum() + "/" +workorderDto.getAllNum());
            return widgetOrderListSubItem;
        }
    }

    ;
}