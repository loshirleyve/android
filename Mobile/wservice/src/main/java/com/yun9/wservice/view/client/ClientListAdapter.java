package com.yun9.wservice.view.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.CacheClientProxy;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.model.Client;
import com.yun9.wservice.view.order.OrderProviderWidget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by li on 2015/6/13.
 */
public class ClientListAdapter extends JupiterAdapter {
    private Context context;
    private LinkedList<Client> clients;

    public ClientListAdapter(Context context, LinkedList<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @Override
    public int getCount() {
        return this.clients.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClientItemLayout clientItemLayout = null;
        final Client client = clients.get(position);

        if (AssertValue.isNotNull(convertView)) {
            clientItemLayout = (ClientItemLayout) convertView;
        } else {
            clientItemLayout = new ClientItemLayout(context);
        }

        if (AssertValue.isNotNull(client)) {
            clientItemLayout.getTitle_TV().setText(client.getName());
            clientItemLayout.getContact_TV().setText(client.getContactman());
            clientItemLayout.getPhone_TV().setText(client.getContactphone());
            clientItemLayout.setTag(client);
            if (client.getCreatedate() != null){
                clientItemLayout.getCreateTimeTv()
                        .setText(DateFormatUtil
                                .format(Long.valueOf(client.getCreatedate()),
                                        StringPool.DATE_FORMAT_DATE));
            }
            // 如果正在代理该机构
            if (ClientProxyCache.getInstance().isProxy()){
                CacheClientProxy proxy = ClientProxyCache.getInstance().getProxy();
                if (proxy.getUserId().equals(client.getClientadminid())
                        && proxy.getInstId().equals(client.getClientinstid())){
                    clientItemLayout.getTitle_TV().setText(client.getName()+"(正在代理)");
                    clientItemLayout.getTitle_TV().setTextColor(clientItemLayout.getResources()
                            .getColor(R.color.title_color));
                    clientItemLayout.getTitle_TV().getPaint().setFakeBoldText(true);
                }
            }
            clientItemLayout.getPhone_LL().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog(client.getContactphone());
                }
            });
        }

        return clientItemLayout;

    }
    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        Client client = clients.get(position);
        if (ClientProxyCache.getInstance().isProxy()){
            CacheClientProxy proxy = ClientProxyCache.getInstance().getProxy();
            if (proxy.getUserId().equals(client.getClientadminid())
                    && proxy.getInstId().equals(client.getClientinstid())){
                if (AssertValue.isNotNullAndNotEmpty(client.getClientinstid())){
                    return ClientActivity.VIEW_TYPE_INITED_PROXY;
                } else {
                    return ClientActivity.VIEW_TYPE_PROXY;
                }
            }
        }
        if (AssertValue.isNotNullAndNotEmpty(client.getClientinstid())){
            return ClientActivity.VIEW_TYPE_INITED_NORMAL;
        } else {
            return ClientActivity.VIEW_TYPE_NORMAL;
        }
    }
    private void dialog(final String phone) {
        if (!AssertValue.isNotNullAndNotEmpty(phone)){
            Toast.makeText(context, R.string.can_not_get_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage("打电话给："+phone);
        builder.setTitle(R.string.app_notice);
        builder.setPositiveButton(R.string.sure, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                call(phone);
            }
        });
        builder.setNegativeButton(R.string.app_cancel, new android.content.DialogInterface.OnClickListener() {
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
        context.startActivity(intent);
    }
}
