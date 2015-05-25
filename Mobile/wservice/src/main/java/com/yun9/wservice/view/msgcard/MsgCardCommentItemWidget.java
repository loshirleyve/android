package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCardComment;

/**
 * Created by huangbinglong on 15/5/25.
 */
public class MsgCardCommentItemWidget extends JupiterRelativeLayout{

    private ImageView userHead_iv;
    private TextView userName_tv;
    private TextView commentTime_tv;
    private TextView commentContent_tv;

    public MsgCardCommentItemWidget(Context context) {
        super(context);
    }

    public MsgCardCommentItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardCommentItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail_comments;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userHead_iv = (ImageView) this.findViewById(R.id.user_head);
        userName_tv = (TextView) this.findViewById(R.id.user_name);
        commentTime_tv = (TextView) this.findViewById(R.id.msg_card_time_tv);
        commentContent_tv = (TextView) this.findViewById(R.id.comment_content);
    }

    public void buildWithData(MsgCardComment msgCardComment) {

    }
}
