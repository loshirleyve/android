package com.yun9.wservice.view.myself;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/22.
 */
public class UserHeadWidget extends JupiterRelativeLayout  {

    private LinearLayout orgLL;

    private LinearLayout docLL;

    private TextView userNameTV;

    private TextView companyTV;

    private TextView orgTV;

    private TextView signTV;

    public UserHeadWidget(Context context) {
        super(context);
    }

    public UserHeadWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserHeadWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_user_header;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.orgLL = (LinearLayout) this.findViewById(R.id.org);
        this.docLL = (LinearLayout) this.findViewById(R.id.sendUserFile);

        this.userNameTV = (TextView) this.findViewById(R.id.user_head_name_tv);
        this.companyTV = (TextView) this.findViewById(R.id.user_head_company);
        this.orgTV = (TextView) this.findViewById(R.id.user_head_dept);
        this.signTV = (TextView) this.findViewById(R.id.user_head_sign);
    }


    public LinearLayout getOrgLL() {
        return orgLL;
    }

    public LinearLayout getDocLL() {
        return docLL;
    }

    public void setOrgLL(LinearLayout orgLL) {
        this.orgLL = orgLL;
    }

    public void setDocLL(LinearLayout docLL) {
        this.docLL = docLL;
    }

    public TextView getUserNameTV() {
        return userNameTV;
    }

    public void setUserNameTV(TextView userNameTV) {
        this.userNameTV = userNameTV;
    }

    public TextView getCompanyTV() {
        return companyTV;
    }

    public void setCompanyTV(TextView companyTV) {
        this.companyTV = companyTV;
    }

    public TextView getOrgTV() {
        return orgTV;
    }

    public void setOrgTV(TextView orgTV) {
        this.orgTV = orgTV;
    }

    public TextView getSignTV() {
        return signTV;
    }

    public void setSignTV(TextView signTV) {
        this.signTV = signTV;
    }
}
