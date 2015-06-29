package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/29.
 */
public class MsgCardDetailPraiseWidget extends JupiterRelativeLayout {

    private LinearLayout praiseLl;

    public MsgCardDetailPraiseWidget(Context context) {
        super(context);
    }

    public MsgCardDetailPraiseWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailPraiseWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail_praise;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        praiseLl = (LinearLayout) findViewById(R.id.items_ll);
    }

    public LinearLayout getPraiseLl() {
        return praiseLl;
    }
}
