package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.manager.SessionManager;
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

import java.util.Date;

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

    private String state;

    private String lastupid = "";

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
                refreshList();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void refreshList() {
        ptrClassicFrameLayout.refreshComplete();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge_record_list;
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return 3;
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
                widget = new RechargeRecordItemWidget(RechargeRecordListActivity.this);
                widget.getSutitleLayout().setTitleText("余额充值");
                widget.getSutitleLayout().setSubTitleText("支付宝支付  " + 100 + ".0元");
                if (position == 1){
                    widget.getSutitleLayout().setTitleText("返券充值");
                    widget.getSutitleLayout().setSubTitleText("支付宝支付  " + 100 + ".0元\n券号: 12345678909876");
                }
                widget.getSutitleLayout().getTimeTv().setText(DateUtil.getDateStr(new Date().getTime(),
                        StringPool.DATE_FORMAT_DATE));
                convertView = widget;
            } else {
                widget = (RechargeRecordItemWidget) convertView;
            }
            return convertView;
        }
    };

}
