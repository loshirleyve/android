package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.viewpagerindicator.CirclePageIndicator;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/5/16.
 */
public class MsgCardDetailToolbarPanelsWidget extends JupiterRelativeLayout{

    private ViewPager viewPager;

    private CirclePageIndicator pageIndicator;

    public MsgCardDetailToolbarPanelsWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarPanelsWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarPanelsWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        pageIndicator = (CirclePageIndicator) this.findViewById(R.id.pageindicator);
        viewPager.setAdapter(new MsgCardDetailToolbarPanelsAdapter(context));
        pageIndicator.setViewPager(viewPager);
    }
}
