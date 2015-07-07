package com.yun9.wservice.view.client;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.CacheClientProxy;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.model.Client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by li on 2015/6/13.
 */
public class ClientListAdapter extends JupiterAdapter {
    private Context context;
    private List<Client> clients;

    public ClientListAdapter(Context context, List<Client> clients) {
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
        Client client = clients.get(position);

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
                if (client.getId().equals(proxy.getUserId())
                        && proxy.getInstId().equals(client.getClientinstid())){
                    clientItemLayout.getTitle_TV().setText(client.getName()+"  (正在代理)");
                    clientItemLayout.getTitle_TV().setTextColor(clientItemLayout.getResources()
                            .getColor(R.color.title_color));
                    clientItemLayout.getTitle_TV().getPaint().setFakeBoldText(true);
                }
            }
        }

        return clientItemLayout;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Client client = clients.get(position);
        if (ClientProxyCache.getInstance().isProxy()){
            CacheClientProxy proxy = ClientProxyCache.getInstance().getProxy();
            if (client.getId().equals(proxy.getUserId())
                    && proxy.getInstId().equals(client.getClientinstid())){
                return ClientActivity.VIEW_TYPE_PROXY;
            }
        }
        return ClientActivity.VIEW_TYPE_NORMAL;
    }
}
