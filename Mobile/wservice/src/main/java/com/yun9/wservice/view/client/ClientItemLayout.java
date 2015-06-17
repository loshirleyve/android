package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/6/11.
 */
public class ClientItemLayout extends JupiterRelativeLayout{

    private TextView title_TV;
    private ImageView contact_IV;
    private TextView contact_TV;
    private ImageView phone_IV;
    private TextView phone_TV;
    private TextView actOrder_BT;

    @Override
    protected int getContextView() {
        return R.layout.widget_client_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        title_TV = (TextView)findViewById(R.id.title_tv);
//        contact_IV = (ImageView)findViewById(R.id.contact_iv);
        contact_TV = (TextView)findViewById(R.id.contact_tv);
//        phone_IV = (ImageView)findViewById(R.id.phone_iv);
        phone_TV = (TextView)findViewById(R.id.phone_tv);
        actOrder_BT = (TextView)findViewById(R.id.actOrder_BT);
    }
    public TextView getTitle_TV() {
        return title_TV;
    }

    public void setTitle_TV(TextView title_TV) {
        this.title_TV = title_TV;
    }

    public ImageView getContact_IV() {
        return contact_IV;
    }

    public void setContact_IV(ImageView contact_IV) {
        this.contact_IV = contact_IV;
    }

    public TextView getContact_TV() {
        return contact_TV;
    }

    public void setContact_TV(TextView contact_TV) {
        this.contact_TV = contact_TV;
    }

    public ImageView getPhone_IV() {
        return phone_IV;
    }

    public void setPhone_IV(ImageView phone_IV) {
        this.phone_IV = phone_IV;
    }

    public TextView getPhone_TV() {
        return phone_TV;
    }

    public void setPhone_TV(TextView phone_TV) {
        this.phone_TV = phone_TV;
    }

    public TextView getActOrder_BT() {
        return actOrder_BT;
    }

    public void setActOrder_BT(Button actOrder_BT) {
        this.actOrder_BT = actOrder_BT;
    }

    public ClientItemLayout(Context context) {
        super(context);
    }

    public ClientItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClientItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
