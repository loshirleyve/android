package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class ClientAddNewSaleManPopLayout extends JupiterRelativeLayout{

    private JupiterListView listView;

    public ClientAddNewSaleManPopLayout(Context context) {
        super(context);
    }

    public ClientAddNewSaleManPopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClientAddNewSaleManPopLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.popup_client_addnewsaleman;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        listView = (JupiterListView) this.findViewById(R.id.user_role_list);
    }

    public JupiterListView getListView() {
        return listView;
    }

    public ClientAddNewSaleManPopLayout setListView(JupiterListView listView) {
        this.listView = listView;
        return this;
    }
}
