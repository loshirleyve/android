package com.yun9.wservice.view.order;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.enums.SourceType;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.State;
import com.yun9.wservice.view.msgcard.MsgCardDetailActivity;
import com.yun9.wservice.view.msgcard.MsgCardDetailCommand;
import com.yun9.wservice.view.org.OrgUserDetailActivity;
import com.yun9.wservice.view.org.OrgUserDetailCommand;
import com.yun9.wservice.view.payment.PaymentOrderActivity;
import com.yun9.wservice.view.payment.PaymentOrderCommand;
import com.yun9.wservice.view.payment.PaymentOrderRemainActivity;
import com.yun9.wservice.view.payment.PaymentResultActivity;
import com.yun9.wservice.view.payment.PaymentResultCommand;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderDetailActivity extends JupiterFragmentActivity {

    public static final String ORDER_DETAIL_NEES_REFRESH = "ORDER_DETAIL_NEES_REFRESH";

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    @ViewInject(id = R.id.order_detail_base_widget)
    private OrderDetailBaseWidget orderDetailBaseWidget;

    @ViewInject(id = R.id.order_detail_process_widget)
    private OrderDetailProcessWidget orderDetailProcessWidget;

    @ViewInject(id = R.id.order_detail_payinfo_widget)
    private OrderDetailPayinfoWidget orderDetailPayinfoWidget;

    @ViewInject(id = R.id.order_detail_attach_widget)
    private OrderDetailAttachWidget orderDetailAttachWidget;

    @ViewInject(id = R.id.order_detail_advisor_widget)
    private OrderDetailAdvisorWidget orderDetailAdvisorWidget;

    @ViewInject(id = R.id.order_detail_provider_widget)
    private OrderDetailProviderWidget orderDetailProviderWidget;

    @ViewInject(id = R.id.order_detail_work_order_list_widget)
    private OrderDetailWorkOrderListWidget orderDetailWorkOrderListWidget;

    @ViewInject(id = R.id.main)
    private RelativeLayout mainRl;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String orderId;

    private Order order;

    private ProgressDialog reloadDataDialog;

    public static void start(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid", orderId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainRl.setVisibility(View.GONE);
        orderId = getIntent().getStringExtra("orderid");
        this.buildView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == JupiterCommand.RESULT_CODE_OK) {
            autoRefresh();
        } else if (AppCache.getInstance().getAsBoolean(ORDER_DETAIL_NEES_REFRESH)) {
            autoRefresh();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    private void buildView() {
        titleBarLayout.getTitleTv().setText(R.string.order_detail);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.this.finish();
            }
        });
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderComplainActivity.start(OrderDetailActivity.this, orderId);
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

    private void refresh() {
        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                reloadData();
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

    private void reloadData() {
        AppCache.getInstance().put(ORDER_DETAIL_NEES_REFRESH, false);
        reloadDataDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryOrderInfoService");
        resource.param("orderid", orderId);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Order order = (Order) response.getPayload();
                reloadData(order);
            }

            @Override
            public void onFailure(Response response) {
                reloadData(new Order());
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                ptrClassicFrameLayout.refreshComplete();
            }
        });
    }

    private void reloadData(final Order order) {
        this.order = order;
        if (!AssertValue.isNotNull(order.getOrder())
                || !State.Order.COMPLETE.equals(order.getOrder().getState())) {
            titleBarLayout.getTitleRight().setVisibility(View.GONE);
        }
        if (order.getOrderproducts() == null
                || order.getOrderproducts().size() == 0) {
            orderDetailBaseWidget.setVisibility(View.GONE);
        } else {
            orderDetailBaseWidget.builWithData(order);
        }
        if (order.getOrderLogs() == null
                || order.getOrderLogs().size() == 0) {
            orderDetailProcessWidget.setVisibility(View.GONE);
        } else {
            orderDetailProcessWidget.buildWithData(order);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getState())) {

            // 不再显示后面的描述
            orderDetailPayinfoWidget.getSutitleLayout()
                    .getHotNitoceTV().setVisibility(View.GONE);
            orderDetailPayinfoWidget.buildWithData(order);

            orderDetailPayinfoWidget.getSutitleLayout()
                    .getTitleTV().setText(CtrlCodeCache.getInstance()
                    .getCtrlcodeName(CtrlCodeDefNo.ORDER_PAY_STATE, order.getOrder().getPaystate()));

            if (State.OrderPayState.COMPLETE.equals(order.getOrder().getPaystate())) {
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().setTextColor(getResources().getColor(R.color.purple_font));
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().setText("查看付款详情");
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().getPaint().setFakeBoldText(false);
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getTitleTV().setTextColor(getResources().getColor(R.color.black));
            } else if (State.OrderPayState.UN_CONFIRM.equals(order.getOrder().getPaystate())) {
                orderDetailPayinfoWidget.getSutitleLayout().getHotNitoceTV().setText("继续支付");
            }

            orderDetailPayinfoWidget.getSutitleLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (State.OrderPayState.WAITING_PAY.equals(order.getOrder().getPaystate())) {
                        PaymentOrderCommand command = new PaymentOrderCommand();
                        command.setSource(PaymentOrderCommand.SOURCE_ORDER);
                        command.setSourceValue(order.getOrder().getId());
                        command.setInstId(order.getOrder().getInstid());
                        PaymentOrderActivity.start(OrderDetailActivity.this, command);
                    } else if (State.OrderPayState.UN_CONFIRM.equals(order.getOrder().getPaystate())) {
                        PaymentOrderCommand command = new PaymentOrderCommand();
                        command.setSource(PaymentOrderCommand.SOURCE_ORDER);
                        command.setSourceValue(order.getOrder().getId());
                        command.setInstId(order.getOrder().getInstid());
                        PaymentOrderRemainActivity.start(OrderDetailActivity.this, command);
                    } else {
                        PaymentResultActivity.start(OrderDetailActivity.this,
                                new PaymentResultCommand(
                                        SourceType.TYPE_ORDER, orderId
                                ));
                    }
                }
            });
        } else {
            orderDetailPayinfoWidget.setVisibility(View.GONE);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && order.getOrder().getCommitdoc() > 0) {
            orderDetailAttachWidget.buildWithData(order);
        } else {
            orderDetailAttachWidget.setVisibility(View.GONE);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getAdviser())) {
            orderDetailAdvisorWidget.buildWitdhData(order);
            orderDetailAdvisorWidget.getContactUsIV().setOnClickListener(onContactUsClick);
            orderDetailAdvisorWidget.getCallUsIv().setOnClickListener(onCallUsClick);
            orderDetailAdvisorWidget.getUserHeadIV().setOnClickListener(onAdvisorUserInfoClick);
            orderDetailAdvisorWidget.getUserNameTV().setOnClickListener(onAdvisorUserInfoClick);
        } else {
            orderDetailAdvisorWidget.setVisibility(View.GONE);
        }

        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getInstid())) {
            orderDetailProviderWidget.buildWitdhData(order);
        } else {
            orderDetailProviderWidget.setVisibility(View.GONE);
        }
        if (order.getWorkorders() == null
                || order.getWorkorders().size() == 0) {
            orderDetailWorkOrderListWidget.setVisibility(View.GONE);
        } else {
            orderDetailWorkOrderListWidget.buildWithData(order.getWorkorders(),OrderDetailActivity.this,order.getOrder().getId());
        }
        mainRl.setVisibility(View.VISIBLE);
        reloadDataDialog.dismiss();
    }

    private View.OnClickListener onContactUsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MsgCardDetailCommand msgCardDetailCommand =
                    new MsgCardDetailCommand().setOrderId(orderId);
            CacheUser user = UserCache.getInstance().getUser(order.getOrder().getAdviser());
            if (user != null) {
                msgCardDetailCommand.setTitle(user.getName());
            }
            MsgCardDetailActivity.start(OrderDetailActivity.this,
                    msgCardDetailCommand);
        }
    };

    private View.OnClickListener onAdvisorUserInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgUserDetailActivity.start(OrderDetailActivity.this,
                    new OrgUserDetailCommand().setUserId(order.getOrder().getAdviser()));
        }
    };

    private View.OnClickListener onCallUsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CacheUser user = UserCache.getInstance().getUser(order.getOrder().getAdviser());
            if (user == null
                    || AssertValue.isNotNullAndNotEmpty(user.getFirstPhone())) {
                showToast("无法获取用户电话号码");
                return;
            }
            final String phone = user.getFirstPhone();
            if (!AssertValue.isNotNullAndNotEmpty(phone)) {
                Toast.makeText(OrderDetailActivity.this, "无法获取用户电话号码", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
            builder.setMessage("打电话给：" + phone);
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    call(phone);
                }
            });
            builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

        private void call(String phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                    phone.replace("-", "")));
            OrderDetailActivity.this.startActivity(intent);
        }
    };
}
