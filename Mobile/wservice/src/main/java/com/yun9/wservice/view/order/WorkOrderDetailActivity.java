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
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderGroup;
import com.yun9.wservice.model.OrderInfo;
import com.yun9.wservice.model.WordOrder;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rxy on 15/8/27.
 */
public class WorkOrderDetailActivity extends JupiterFragmentActivity {


    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;


    @ViewInject(id = R.id.workorderlistview)
    private JupiterListView workorderlistview;

    @BeanInject
    private ResourceFactory resourceFactory;

    private WorkOrderCommand command;

    private String orderid;

    public static void start(Context context,WorkOrderCommand command ) {
        Intent intent = new Intent(context, WorkOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_workorder_detail;
    }

    private void buildView() {
        command = (WorkOrderCommand) this.getIntent().getSerializableExtra("command");
        orderid=command.getOrderid();
        titleBarLayout.getTitleTv().setText(R.string.workorder_detail);
        titleBarLayout.getTitleRight().setVisibility(View.GONE);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOrderDetailActivity.this.finish();
            }
        });
        refreshWorkOrderInfos();
    }

    private void refreshWorkOrderInfos() {

        Resource resource = resourceFactory.create("QueryWorkordersByNoAndSource");
        resource.param("no", command.getWorkorderno());
        resource.param("source",command.getSource());
        resource.param("sourcevalue",command.getOrderid());

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<WordOrder> workorderInfos = (List<WordOrder>) response.getPayload();
                workorderlistview.setAdapter(new WordOrderAdapter(workorderInfos));
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(WorkOrderDetailActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }


    private  class WordOrderAdapter extends  JupiterAdapter{

        private List<WordOrder> workorderInfos;


        public WordOrderAdapter(List<WordOrder> workorderInfos)
        {
            this.workorderInfos=workorderInfos;
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
            widgetOrderListItem.buildWithData(orderid,workorderInfos.get(position));
            return widgetOrderListItem;
        }
    };

}