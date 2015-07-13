package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.CtrlCode;
import com.yun9.wservice.model.RechargeRecord;

import java.util.Date;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by huangbinglong on 15/6/30.
 */
public class RechargeRecordListActivity extends JupiterFragmentActivity{

    @ViewInject(id= R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id=R.id.record_list_ptr)
    private ListView recordLV;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private List<RechargeRecord> records;

    private String state;

    public static void start(Context context,String state,String stateName) {
        Intent intent = new Intent(context, RechargeRecordListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        bundle.putString("stateName", stateName);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getIntent().getStringExtra("state");
        String stateName = getIntent().getStringExtra("stateName");
        if (AssertValue.isNotNullAndNotEmpty(stateName)) {
            titleBarLayout.getTitleTv().setText(stateName+"记录");
        }
        buildView();
    }

    private void buildView() {
        recordLV.setAdapter(adapter);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeRecordListActivity.this.finish();
            }
        });
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
         autoRefresh();
    }

    private void autoRefresh(){
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        }, 100);
    }

    private void refresh() {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh();
            }
        }, 100);
    }

    private void completeRefresh() {
        Resource resource = resourceFactory.create("QueryRechargeService");
        resource.param("state", state);
        resource.param("userid", sessionManager.getUser().getId());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                records = (List<RechargeRecord>) response.getPayload();
            }
            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }
            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge_record_list;
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (records != null){
                return records.size();
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
            RechargeRecordItemWidget widget;
            if (convertView == null){
                RechargeRecord record = records.get(position);
                widget = new RechargeRecordItemWidget(RechargeRecordListActivity.this);
                widget.getSutitleLayout()
                        .setTitleText(
                                CtrlCodeCache.getInstance().getCtrlcodeName(
                                        CtrlCodeDefNo.ACCOUNT_TYPE,record.getAccounttype()
                                ) + "充值");
                widget.getSutitleLayout().setSubTitleText(record.getRechargename() +
                        "支付  " + record.getAmount() + "元");
                widget.getSutitleLayout().getTimeTv().setText(DateUtil.getDateStr(record.getExpirydate()));
                widget.getRechargeStateTv().setText(CtrlCodeCache.getInstance()
                                                    .getCtrlcodeName(CtrlCodeDefNo.RECHARGE_DETAIL_STATE,
                                                            record.getState()));
                widget.getRechardIdTv().setText(record.getId());
                convertView = widget;
            }
            return convertView;
        }
    };

}
