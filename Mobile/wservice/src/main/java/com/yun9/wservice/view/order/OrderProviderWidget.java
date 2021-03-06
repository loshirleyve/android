package com.yun9.wservice.view.order;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.renderscript.Script;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderProviderWidget extends JupiterRelativeLayout {

    private TextView instNameTV;
    private TextView instPhoneTV;
    private LinearLayout contactUsLL;

    private String phone;

    public OrderProviderWidget(Context context) {
        super(context);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_provider;
    }

    public void buildWithData(Order order) {
        buildWithData(order.getOrder().getInstid());
    }

    public void buildWithData(String instId) {
        CacheInst inst = InstCache.getInstance().getInst(instId);
        if (inst != null) {
            this.phone = inst.getTel();
            instNameTV.setText(inst.getInstname());
            instPhoneTV.setText("电话 " + inst.getTel());// 电话号码
        }
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        instNameTV = (TextView) this.findViewById(R.id.inst_name_tv);
        instPhoneTV = (TextView) this.findViewById(R.id.inst_phone_tv);
        contactUsLL = (LinearLayout) this.findViewById(R.id.contact_us_ll);
        buildView();
    }

    private void buildView() {
        contactUsLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }

    private void dialog() {
        if (!AssertValue.isNotNullAndNotEmpty(phone)){
            Toast.makeText(this.mContext,"无法获取机构电话号码",Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage("打电话给："+phone);
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                call();
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

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                phone.replace("-","")));
        OrderProviderWidget.this.getContext().startActivity(intent);
    }
}
