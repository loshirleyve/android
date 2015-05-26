package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardComment;

import java.util.List;

/**
 * Created by huangbinglong on 15/5/25.
 */
public class MsgCardCommentListWidget extends JupiterRelativeLayout{

    private MsgCard msgCard;

    private LinearLayout commentsLayout;

    public MsgCardCommentListWidget(Context context) {
        super(context);
    }

    public MsgCardCommentListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardCommentListWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail_comments;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        commentsLayout = (LinearLayout) this.findViewById(R.id.msg_card_detail_comments_ll);
    }

    public void buildWithData(MsgCard msgCard) {
        this.msgCard = msgCard;
        commentsLayout.removeAllViews();
        List<MsgCardComment> comments = msgCard.getCommentlist();
        if (comments != null) {
            for (MsgCardComment comment : comments) {
                MsgCardCommentItemWidget itemWidget = new MsgCardCommentItemWidget(getContext());
                itemWidget.buildWithData(comment);
                commentsLayout.addView(itemWidget);
            }
        }
    }

    private void appendDeviceLine() {
        View line = new View(getContext());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(getResources().getColor(R.color.devide_line));
        this.addView(line);
    }
}
