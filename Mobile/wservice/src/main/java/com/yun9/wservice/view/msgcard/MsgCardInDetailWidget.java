package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterSegmentedGroup;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

/**
 * Created by Leon on 15/4/29.
 */
public class MsgCardInDetailWidget extends JupiterRelativeLayout {

    private MsgCardWidget msgCardWidget;

    private JupiterSegmentedGroup segmentedGroup;

    public MsgCardInDetailWidget(Context context) {
        super(context);
    }

    public MsgCardInDetailWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardInDetailWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        this.msgCardWidget = (MsgCardWidget) this.findViewById(R.id.msg_card_info);
        this.segmentedGroup = (JupiterSegmentedGroup) this.findViewById(R.id.msg_card_detail_tab);
    }

    public void buildWithData(MsgCard msgCard) {
        msgCardWidget.buildWithData(msgCard);
        MsgCardDetailSegmentedItemAdapter segmentedItemAdapter = new MsgCardDetailSegmentedItemAdapter(this.getContext(), msgCard);
        MsgCardDetailViewPagerAdapter viewPagerAdapter = new MsgCardDetailViewPagerAdapter(this.getContext(),msgCard);
        this.segmentedGroup.setAdapter(segmentedItemAdapter);
        this.segmentedGroup.setTabItemAdapter(viewPagerAdapter);
    }

    public JupiterSegmentedGroup getSegmentedGroup() {
        return segmentedGroup;
    }

    public void setSegmentedGroup(JupiterSegmentedGroup segmentedGroup) {
        this.segmentedGroup = segmentedGroup;
    }
}
