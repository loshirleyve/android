package com.yun9.wservice.view.myself;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/22.
 */
public class UserHeadWidget extends JupiterRelativeLayout  {

    private LinearLayout orgLL;

    private LinearLayout docLL;

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
    }


    public LinearLayout getOrgLL() {
        return orgLL;
    }

    public LinearLayout getDocLL() {
        return docLL;
    }
}