package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class ClientAddNewSaleManPopLayout extends JupiterRelativeLayout{

    private TextView tx_addNewAdvisor;
    private TextView tx_addNewSaleMan;

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
        tx_addNewAdvisor = (TextView) this.findViewById(R.id.addNewAdvisor);
        tx_addNewSaleMan = (TextView) this.findViewById(R.id.addNewSaleMan);
    }

    public TextView getTx_addNewAdvisor() {
        return tx_addNewAdvisor;
    }

    public ClientAddNewSaleManPopLayout setTx_addNewAdvisor(TextView tx_addNewAdvisor) {
        this.tx_addNewAdvisor = tx_addNewAdvisor;
        return this;
    }

    public TextView getTx_addNewSaleMan() {
        return tx_addNewSaleMan;
    }

    public ClientAddNewSaleManPopLayout setTx_addNewSaleMan(TextView tx_addNewSaleMan) {
        this.tx_addNewSaleMan = tx_addNewSaleMan;
        return this;
    }
}
