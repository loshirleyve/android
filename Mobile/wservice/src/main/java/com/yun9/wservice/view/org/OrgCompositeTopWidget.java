package com.yun9.wservice.view.org;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgCompositeTopWidget extends JupiterRelativeLayout {

    private JupiterRowStyleSutitleLayout myselfLL;

    private JupiterRowStyleSutitleLayout orgHrLL;

    private JupiterRowStyleSutitleLayout orgGroupLL;

    public OrgCompositeTopWidget(Context context) {
        super(context);
    }

    public OrgCompositeTopWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrgCompositeTopWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_org_composite_top;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        myselfLL = (JupiterRowStyleSutitleLayout) findViewById(R.id.myself);
        orgGroupLL = (JupiterRowStyleSutitleLayout) findViewById(R.id.org_group);
        orgHrLL = (JupiterRowStyleSutitleLayout) findViewById(R.id.org_hr);
    }

    public JupiterRowStyleSutitleLayout getMyselfLL() {
        return myselfLL;
    }

    public JupiterRowStyleSutitleLayout getOrgHrLL() {
        return orgHrLL;
    }

    public JupiterRowStyleSutitleLayout getOrgGroupLL() {
        return orgGroupLL;
    }
}
