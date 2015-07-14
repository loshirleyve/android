package com.yun9.wservice.view.payment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.enums.RechargeNo;
import com.yun9.wservice.enums.RechargeState;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.model.AddRechargeResult;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.model.CtrlCode;
import com.yun9.wservice.model.RechargeRecord;

import java.util.ArrayList;
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

    public static final String DEFAULT_STATE_NAME = "全部";

    public static final String CODE_RECHARGE = "0001";

    public static final String CODE_PAY = "0002";

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

    @BeanInject
    private AlipayManager alipayManager;

    private List<RechargeRecord> records;

    private String rowid;

    private String state;

    public static void start(Context context,String state,String stateName) {
        Intent intent = new Intent(context, RechargeRecordListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        bundle.putString("stateName", AssertValue.isNotNullAndNotEmpty(stateName)?stateName:DEFAULT_STATE_NAME);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = getIntent().getStringExtra("state");
        String stateName = getIntent().getStringExtra("stateName");
        if (AssertValue.isNotNullAndNotEmpty(stateName)) {
            titleBarLayout.getTitleTv().setText("充值记录("+stateName+")");
        }
        records = new ArrayList<>();
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
        if (AssertValue.isNotNullAndNotEmpty(rowid)){
            resource.page().setRowid(rowid);
        }
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<RechargeRecord> tempList = (List<RechargeRecord>) response.getPayload();
                if (tempList != null
                        && tempList.size() > 0) {
                    rowid = tempList.get(0).getId();
                    records.addAll(0, tempList);
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
                final RechargeRecord record = records.get(position);
                widget = new RechargeRecordItemWidget(RechargeRecordListActivity.this);
                widget.getSutitleLayout()
                        .setTitleText(
                                CtrlCodeCache.getInstance().getCtrlcodeName(
                                        CtrlCodeDefNo.ACCOUNT_TYPE, record.getAccounttype()
                                ) + "充值");
                widget.getSutitleLayout().setSubTitleText(record.getRechargename() +
                        "支付  " + record.getAmount() + "元");
                widget.getSutitleLayout().getTimeTv().setText(DateUtil.getDateStr(record.getExpirydate()));
                widget.getRechargeStateTv().setText(CtrlCodeCache.getInstance()
                        .getCtrlcodeName(CtrlCodeDefNo.RECHARGE_DETAIL_STATE,
                                record.getState()));
                widget.getRechardIdTv().setText(record.getId());
                if (RechargeState.UN_ARRIVE.equals(record.getState())){
                    widget.getSutitleLayout().setTitleText(widget.getSutitleLayout().getTitleTV().getText()
                                                            +"("+
                                    CtrlCodeCache.getInstance()
                                            .getCtrlcodeName(CtrlCodeDefNo.RECHARGE_DETAIL_STATE,
                                                    record.getState())+
                                    ")"
                                                        );
                    widget.getRechargeStateTv().setBackgroundColor(
                            getResources().getColor(R.color.title_color)
                    );
                    widget.getRechargeStateTv().setTextColor(
                            getResources().getColor(R.color.whites)
                    );
                    widget.getRechargeStateTv().setText("重新支付");
                    widget.getRechargeStateTv().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rechargeAgain(record);
                        }
                    });
                }
                convertView = widget;
            }
            return convertView;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == JupiterCommand.RESULT_CODE_OK) {
            records.clear();
            rowid = null;
            autoRefresh();
        }
    }

    private void rechargeAgain(RechargeRecord record) {
        if (RechargeNo.TYPE_ALIPAY.equals(record.getRechargeno())){
            payByAlipay(record);
        } else if (RechargeNo.TYPE_ALIPAY.equals(record.getRechargeno())) {
            showToast("功能正在研发中..");
        }
    }

    /**
     * 使用支付宝支付
     * @param record
     */
    private void payByAlipay(final RechargeRecord record) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "支付中，请稍候...", true);
        AlipayManager.OrderInfo orderInfo =
                new AlipayManager.OrderInfo("余额充值",sessionManager.getUser().getName()
                        +"于"+ DateUtil.getStringToday()+"充值"+record.getAmount()+"元",
                        getCallbackid(record),record.getAmount()+"");
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                RechargeResultCommand command =
                        new RechargeResultCommand(record.getId(), null,
                                record.getRechargename(), record.getAmount());
                switch (msg.what) {
                    case AlipayManager.SDK_PAY_FLAG: {
                        AlipayManager.PayResult payResult = new AlipayManager
                                .PayResult((String) msg.obj);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();
                        String memo = payResult.getMemo();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            command.setStateName("支付成功");
                            RechargeResultActivity.start(RechargeRecordListActivity.this,command);
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                command.setStateName("支付结果确认中");
                                RechargeResultActivity.start(RechargeRecordListActivity.this,command);
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                showToast("支付失败\n" + memo);
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
                registerDialog.dismiss();
            }
        };
        alipayManager.pay(this,orderInfo,handler);
    }

    private String getCallbackid(RechargeRecord record) {
        return record.getId()+"_"+CODE_RECHARGE;
    }

}
