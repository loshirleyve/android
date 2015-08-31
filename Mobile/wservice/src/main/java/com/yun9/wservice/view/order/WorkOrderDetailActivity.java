package com.yun9.wservice.view.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.command.JupiterCommand;
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
import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderGroup;
import com.yun9.wservice.model.OrderInfo;
import com.yun9.wservice.model.WordOrder;
import com.yun9.wservice.model.WorkOrderComment;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by rxy on 15/8/27.
 */
public class WorkOrderDetailActivity extends JupiterFragmentActivity {


    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrFrame;

    @ViewInject(id = R.id.workorderlistview)
    private PagingListView workorderlistview;


    private LinkedList<WordOrder> workorderinfos = new LinkedList<>();
    private WordOrderAdapter wordOrderAdapter;

    @BeanInject
    private ResourceFactory resourceFactory;

    private WorkOrderCommand command;

    private String orderid;

    public static void start(Context context, WorkOrderCommand command) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_workorder_detail;
    }


    protected void initViews() {
        command = (WorkOrderCommand) this.getIntent().getSerializableExtra("command");
        orderid = command.getOrderid();
        titleBarLayout.getTitleTv().setText("工单列表");
        titleBarLayout.getTitleRight().setVisibility(View.GONE);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOrderDetailActivity.this.finish();
            }
        });
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                workorderinfos.clear();
                workorderlistview.setHasMoreItems(true);
                refreshWorkOrderInfos(null, Page.PAGE_DIR_PULL);

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);

        wordOrderAdapter = new WordOrderAdapter(workorderinfos);
        workorderlistview.setAdapter(wordOrderAdapter);
        workorderlistview.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(workorderinfos)) {
                    WordOrder wordOrder = workorderinfos.get(workorderinfos.size() - 1);
                    refreshWorkOrderInfos(wordOrder.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    workorderlistview.onFinishLoading(true);
                }
            }
        });

    }

    private void refreshWorkOrderInfos(String rowid, final String dir) {
        Resource resource = resourceFactory.create("QueryWorkordersByNoAndSource");
        resource.param("no", command.getWorkorderno());
        resource.param("source", command.getSource());
        resource.param("sourcevalue", command.getOrderid());
        resource.page().setDir(dir).setRowid(rowid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<WordOrder> workorderInfos = (List<WordOrder>) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(workorderInfos) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = workorderInfos.size(); i > 0; i--) {
                        WordOrder wordOrder = workorderInfos.get(i - 1);
                        workorderinfos.addFirst(wordOrder);
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(workorderInfos) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (WordOrder wordOrder : workorderInfos) {
                        workorderinfos.addLast(wordOrder);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(workorderInfos) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
                    workorderlistview.onFinishLoading(false);
                }

                wordOrderAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(WorkOrderDetailActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                workorderlistview.onFinishLoading(true);
                mPtrFrame.refreshComplete();
            }
        });
    }


    private class WordOrderAdapter extends JupiterAdapter {

        private List<WordOrder> workorderInfos;


        public WordOrderAdapter(List<WordOrder> workorderInfos) {
            this.workorderInfos = workorderInfos;
        }

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(workorderInfos)) {
                return workorderInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return workorderInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderDetailWorkOrderWidget widgetOrderListItem = null;

            if (AssertValue.isNotNull(convertView)) {
                widgetOrderListItem = (OrderDetailWorkOrderWidget) convertView;
            } else {
                widgetOrderListItem = new OrderDetailWorkOrderWidget(WorkOrderDetailActivity.this);
            }
            widgetOrderListItem.buildWithData(orderid, workorderInfos.get(position));
            return widgetOrderListItem;
        }
    }

    ;

}