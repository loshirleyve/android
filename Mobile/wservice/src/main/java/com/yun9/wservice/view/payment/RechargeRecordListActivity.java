package com.yun9.wservice.view.payment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.enums.RechargeNo;
import com.yun9.wservice.enums.RechargeState;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.manager.WeChatManager;
import com.yun9.wservice.model.AddRechargeResult;
import com.yun9.wservice.model.RechargeRecord;
import com.yun9.wservice.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
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

    @ViewInject(id= R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id=R.id.record_list_ptr)
    private PagingListView recordLV;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private AlipayManager alipayManager;

    @BeanInject
    private WeChatManager weChatManager;

    private List<RechargeRecord> records;

    private String pullRowid = null;

    private String pushRowid = null;

    private String state;

    private IWXAPI iwxapi;

    private ProgressDialog wechatDialog;

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
        iwxapi = WXAPIFactory.createWXAPI(this, WeChatManager.APP_ID);
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
                recordLV.setHasMoreItems(true);
                pullRowid = null;
                records.clear();
                refresh(pullRowid, Page.PAGE_DIR_PULL);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        recordLV.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(pushRowid)) {
                    refresh(pushRowid, Page.PAGE_DIR_PUSH);
                } else {
                    recordLV.onFinishLoading(true);
                }
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

    private void refresh(final String rowid, final String dir) {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh(rowid, dir);
            }
        }, 100);
    }

    private void completeRefresh(String rowid, final String dir) {
        final Resource resource = resourceFactory.create("QueryRechargeService");
        resource.param("state", state);
        resource.param("userid", sessionManager.getUser().getId());
        resource.page().setDir(dir).setRowid(rowid);
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<RechargeRecord> tempList = (List<RechargeRecord>) response.getPayload();
                if (tempList != null
                        && tempList.size() > 0) {
                    if (Page.PAGE_DIR_PULL.equals(dir)) {
                        pullRowid = tempList.get(0).getId();
                        records.addAll(0, tempList);
                        if (!AssertValue.isNotNullAndNotEmpty(pushRowid)) {
                            pushRowid = tempList.get(tempList.size() - 1).getId();
                        }

                        if (tempList.size() < Integer.valueOf(resource.page().getSize())) {
                            recordLV.setHasMoreItems(false);
                        }
                    } else {
                        pushRowid = tempList.get(tempList.size() - 1).getId();
                        records.addAll(tempList);
                    }
                } else if (Page.PAGE_DIR_PULL.equals(dir)) {
                    showToast("没有新数据");
                } else if (Page.PAGE_DIR_PUSH.equals(dir)) {
                    recordLV.setHasMoreItems(false);
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
                recordLV.onFinishLoading(true);
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
            final RechargeRecord record = records.get(position);
            if (convertView == null){
                widget = new RechargeRecordItemWidget(RechargeRecordListActivity.this);
                convertView = widget;
            } else {
                widget = (RechargeRecordItemWidget) convertView;
            }
            widget.getSutitleLayout()
                    .setTitleText(
                            CtrlCodeCache.getInstance().getCtrlcodeName(
                                    CtrlCodeDefNo.ACCOUNT_TYPE, record.getAccounttype()
                            ) + "充值");
            widget.getSutitleLayout().setSubTitleText(record.getTypeName() +
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
            return convertView;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == JupiterCommand.RESULT_CODE_OK) {
            records.clear();
            pullRowid = null;
            setResult(JupiterCommand.RESULT_CODE_OK);
            autoRefresh();
        }
    }

    private void rechargeAgain(final RechargeRecord record) {
        final Resource resource = resourceFactory.create("AddRechargeD");
        resource.param("rechargeId", record.getId());
        resource.param("userId", sessionManager.getUser().getId());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                AddRechargeResult rechargeResult = (AddRechargeResult) response.getPayload();
                record.setId(rechargeResult.getCallbackid());
                payBy(record);
            }

            @Override
            public void onFailure(Response response) {
                showToast("无法获取充值ID："+response.getCause());
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void payBy(RechargeRecord record) {
        if (RechargeNo.TYPE_ALIPAY.equals(record.getTypecode())){
            payByAlipay(record);
        } else if (RechargeNo.TYPE_WEIXIN.equals(record.getTypecode())) {
            if(!iwxapi.isWXAppInstalled())
            {
                showToast("您没有安装微信！");
                return;
            }

            if(!iwxapi.isWXAppSupportAPI())
            {
                showToast("当前微信版本不支持支付功能");
                return;
            }
            payByWeChat(record);
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
                        record.getId(),record.getAmount()+"");
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                RechargeResultCommand command =
                        new RechargeResultCommand(record.getId(), null,
                                record.getTypeName(), record.getAmount());
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

    /**
     * 使用微信进行支付
     *
     */
    private void payByWeChat(final RechargeRecord record) {
        iwxapi.registerApp(WeChatManager.APP_ID);
        wechatDialog = ProgressDialog.show(this, null, "微信充值中，请稍候...", true);
        WeChatManager.OrderInfo orderInfo = new WeChatManager.OrderInfo("余额充值", "移办通充值",
                record.getId(), record.getAmount() + "");
        weChatManager.pay(RechargeRecordListActivity.this, orderInfo, new WeChatManager.HttpResponseCallback() {
            @Override
            public void onSuccess(byte[] bytes) {
                PayReq req = weChatManager.getReq(RechargeRecordListActivity.this, bytes);
                if (req != null) {
                    boolean hasApp = iwxapi.sendReq(req);
                    WXPayEntryActivity.setWeChatCallback(new WXPayEntryActivity.WeChatCallback() {
                        @Override
                        public void onResp(BaseResp resp) {
                            RechargeRecordListActivity.this.onResp(record, resp);
                        }
                    });
                    if (!hasApp) {
                        wechatDialog.dismiss();
                        showToast("打开微信失败。");
                    }
                } else {
                    wechatDialog.dismiss();
                    showToast("微信支付下单失败！");
                }
            }

            @Override
            public void onFailure(byte[] bytes, Throwable throwable) {
                showToast("获取微信预付码错误:" + throwable.getMessage());
            }
        });
    }


    private void onResp(RechargeRecord record,BaseResp baseResp) {
        if (wechatDialog != null && wechatDialog.isShowing()) {
            wechatDialog.dismiss();
            wechatDialog = null;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            RechargeResultCommand command =
                    new RechargeResultCommand(record.getId(), null,
                            record.getTypeName(), record.getAmount());
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                command.setStateName("支付成功");
                RechargeResultActivity.start(RechargeRecordListActivity.this, command);
                return;
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_COMM) {
                showToast("支付失败\n" + baseResp.errStr);
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                showToast("用户取消操作。");
            }
        }
    }

}
