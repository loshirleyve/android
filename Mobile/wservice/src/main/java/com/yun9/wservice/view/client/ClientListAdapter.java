package com.yun9.wservice.view.client;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.model.Client;

import java.util.List;

/**
 * Created by li on 2015/6/13.
 */
public class ClientListAdapter extends JupiterAdapter {
    private Context context;
    private List<Client> clients;

    public ClientListAdapter(Context context, List<Client> clients){
        this.context = context;
        this.clients = clients;
    }
    @Override
    public int getCount() {
        return clients.size();
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
        if(AssertValue.isNotNull(convertView)){
            clientItemLayout = (ClientItemLayout)convertView;
        }else {
            clientItemLayout = new ClientItemLayout(context);
        }

       for (int i = 0; i < clients.size(); i++){
           clientItemLayout.getTitle_TV().setText(clients.get(i).getName());
           clientItemLayout.getContact_TV().setText(clients.get(i).getId());
           clientItemLayout.getPhone_TV().setText(clients.get(i).getNo());
       }

        return clientItemLayout;

    }
}
