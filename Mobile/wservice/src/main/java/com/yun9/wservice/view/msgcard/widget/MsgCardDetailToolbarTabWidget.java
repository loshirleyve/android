package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/5/16.
 */
public class MsgCardDetailToolbarTabWidget extends JupiterRelativeLayout {

    private RelativeLayout praiseLayout;
    private RelativeLayout forwardLayout;
    private RelativeLayout commentLayout;
    private RelativeLayout actionLayout;

    private ImageView msgCardPraiseIv;

    public MsgCardDetailToolbarTabWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarTabWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_tabs;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        praiseLayout = (RelativeLayout) this.findViewById(R.id.praise_rl);
        forwardLayout = (RelativeLayout) this.findViewById(R.id.fw_rl);
        commentLayout = (RelativeLayout) this.findViewById(R.id.comm_rl);
        actionLayout = (RelativeLayout) this.findViewById(R.id.action_rl);
        msgCardPraiseIv =(ImageView)this.findViewById(R.id.praise_iv_item);
    }

    public ImageView getMsgCardPraiseIv() {
        return msgCardPraiseIv;
    }

    public void setMsgCardPraiseIv(ImageView msgCardPraiseIv) {
        this.msgCardPraiseIv = msgCardPraiseIv;
    }

    public RelativeLayout getPraiseLayout() {
        return praiseLayout;
    }

    public RelativeLayout getForwardLayout() {
        return forwardLayout;
    }

    public RelativeLayout getCommentLayout() {
        return commentLayout;
    }

    public RelativeLayout getActionLayout() {
        return actionLayout;
    }
}
