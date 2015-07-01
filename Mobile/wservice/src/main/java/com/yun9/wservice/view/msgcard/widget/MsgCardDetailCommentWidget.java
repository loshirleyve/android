package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardComment;

import java.util.List;

/**
 * Created by huangbinglong on 15/5/25.
 */
public class MsgCardDetailCommentWidget extends JupiterRelativeLayout {

    private LinearLayout commonLl;

    public MsgCardDetailCommentWidget(Context context) {
        super(context);
    }

    public MsgCardDetailCommentWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailCommentWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail_common;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        commonLl = (LinearLayout) findViewById(R.id.items_ll);
    }

    public LinearLayout getCommonLl() {
        return commonLl;
    }
}
