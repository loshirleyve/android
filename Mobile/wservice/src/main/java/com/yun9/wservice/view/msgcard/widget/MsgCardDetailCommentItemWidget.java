package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/27/15.
 */
public class MsgCardDetailCommentItemWidget extends JupiterRelativeLayout{

    private RelativeLayout leftLl;
    private ImageView leftIv;
    private TextView leftTitleTv;
    private TextView leftTitleTipTv;
    private TextView leftTimeTv;
    private TextView leftContentTv;

    private RelativeLayout rightLl;
    private ImageView rightIv;
    private TextView rightTimeTv;
    private TextView rightContentTv;

    public MsgCardDetailCommentItemWidget(Context context) {
        super(context);
    }

    public MsgCardDetailCommentItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailCommentItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_comment_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        leftLl = (RelativeLayout) this.findViewById(R.id.left_ll);
        leftIv = (ImageView) this.findViewById(R.id.left_iv);
        leftTitleTv = (TextView) this.findViewById(R.id.left_title_tv);
        leftTitleTipTv = (TextView) this.findViewById(R.id.left_title_tip_tv);
        leftTimeTv = (TextView) this.findViewById(R.id.left_time_tv);
        leftContentTv = (TextView) this.findViewById(R.id.left_content_tv);

        rightLl = (RelativeLayout) this.findViewById(R.id.right_ll);
        rightIv = (ImageView) this.findViewById(R.id.right_iv);
        rightTimeTv = (TextView) this.findViewById(R.id.right_time_tv);
        rightContentTv = (TextView) this.findViewById(R.id.right_content_tv);

    }

    public RelativeLayout getLeftLl() {
        return leftLl;
    }

    public ImageView getLeftIv() {
        return leftIv;
    }

    public TextView getLeftTitleTv() {
        return leftTitleTv;
    }

    public TextView getLeftTitleTipTv() {
        return leftTitleTipTv;
    }

    public TextView getLeftTimeTv() {
        return leftTimeTv;
    }

    public TextView getLeftContentTv() {
        return leftContentTv;
    }

    public RelativeLayout getRightLl() {
        return rightLl;
    }

    public ImageView getRightIv() {
        return rightIv;
    }

    public TextView getRightTimeTv() {
        return rightTimeTv;
    }

    public TextView getRightContentTv() {
        return rightContentTv;
    }
}
