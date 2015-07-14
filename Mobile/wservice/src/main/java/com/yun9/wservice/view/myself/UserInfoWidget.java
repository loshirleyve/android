package com.yun9.wservice.view.myself;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRoundImageView;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/6/25.
 */
public class UserInfoWidget extends JupiterRelativeLayout {

    private LinearLayout userHeadLL;
    private TextView userHeadTV;
    private JupiterRoundImageView userHeadIV;

    @ViewInject(id = R.id.user_name)
    private JupiterRowStyleSutitleLayout userName;

    @ViewInject(id = R.id.agency)
    private JupiterRowStyleSutitleLayout agency;

    @ViewInject(id = R.id.department)
    private JupiterRowStyleSutitleLayout department;

    @ViewInject(id = R.id.user_signature)
    private JupiterRowStyleSutitleLayout signature;

    @ViewInject(id = R.id.user_password)
    private JupiterRowStyleSutitleLayout password;

    public UserInfoWidget(Context context) {
        super(context);
    }

    public UserInfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserInfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_user_info;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userHeadLL = (LinearLayout) this.findViewById(R.id.userHeadLL);
        userHeadTV = (TextView) this.findViewById(R.id.userHeadTV);
        userHeadIV = (JupiterRoundImageView) this.findViewById(R.id.userHeadIV);
    }

    public JupiterRowStyleSutitleLayout getPassword() {
        return password;
    }

    public void setPassword(JupiterRowStyleSutitleLayout password) {
        this.password = password;
    }

    public JupiterRowStyleSutitleLayout getUserName() {
        return userName;
    }

    public void setUserName(JupiterRowStyleSutitleLayout userName) {
        this.userName = userName;
    }

    public JupiterRowStyleSutitleLayout getSignature() {
        return signature;
    }

    public void setSignature(JupiterRowStyleSutitleLayout signature) {
        this.signature = signature;
    }

    public JupiterRowStyleSutitleLayout getDepartment() {
        return department;
    }

    public void setDepartment(JupiterRowStyleSutitleLayout department) {
        this.department = department;
    }

    public JupiterRowStyleSutitleLayout getAgency() {
        return agency;
    }

    public void setAgency(JupiterRowStyleSutitleLayout agency) {
        this.agency = agency;
    }

    public LinearLayout getUserHeadLL() {
        return userHeadLL;
    }

    public void setUserHeadLL(LinearLayout userHeadLL) {
        this.userHeadLL = userHeadLL;
    }

    public TextView getUserHeadTV() {
        return userHeadTV;
    }

    public void setUserHeadTV(TextView userHeadTV) {
        this.userHeadTV = userHeadTV;
    }


    public ImageView getUserHeadIV() {
        return userHeadIV;
    }

    public void setUserHeadIV(JupiterRoundImageView userHeadIV) {
        this.userHeadIV = userHeadIV;
    }
}
