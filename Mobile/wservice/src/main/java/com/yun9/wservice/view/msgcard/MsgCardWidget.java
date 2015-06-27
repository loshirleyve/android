package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardWidget extends JupiterRelativeLayout {

    private TextView contentTV;
    private TextView locationTV;
    private TextView timeTV;
    private RelativeLayout praiseRL;
    private RelativeLayout fwRL;
    private RelativeLayout commentRL;
    private RelativeLayout actionRL;

    private TextView praiseNumTV;
    private TextView fwNumTV;
    private TextView commentNumTV;
    private TextView actionNumTV;

    private TextView lastCommentContentTV;

    private ImageView praiseIV;

    // 数据实体
    private MsgCard msgCard;

    public MsgCardWidget(Context context) {
        super(context);
    }

    public MsgCardWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

        contentTV = (TextView) this.findViewById(R.id.msg_card_content_tv);
        locationTV = (TextView) this.findViewById(R.id.msg_card_location_tv);
        timeTV = (TextView) this.findViewById(R.id.msg_card_time_tv);

        praiseRL = (RelativeLayout) this.findViewById(R.id.praise_rl);
        praiseNumTV = (TextView) this.findViewById(R.id.praise_num_tv);
        praiseIV = (ImageView) this.findViewById(R.id.praise_iv);

        fwRL = (RelativeLayout) this.findViewById(R.id.fw_rl);
        fwNumTV = (TextView) this.findViewById(R.id.fw_num_tv);

        commentRL = (RelativeLayout) this.findViewById(R.id.comm_rl);
        commentNumTV = (TextView) this.findViewById(R.id.comm_num_tv);

        actionRL = (RelativeLayout) this.findViewById(R.id.action_rl);

        lastCommentContentTV = (TextView) this.findViewById(R.id.msg_card_lastcomment_content_tv);

        this.initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.MsgCardWidget);

        try{
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showAttachment)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showAttachment,false);
                View view = this.findViewById(R.id.msg_card_image);
                if (showMainImage){
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showLocation)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showLocation,false);
                View view = this.findViewById(R.id.location_rl);
                if (showMainImage){
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showToolbar)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showToolbar,false);
                View view = this.findViewById(R.id.toolbar);
                if (showMainImage){
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }
            }
            if (typedArray.hasValue(R.styleable.MsgCardWidget_showLastComment)){
                boolean showMainImage = typedArray.getBoolean(R.styleable.MsgCardWidget_showLastComment,false);
                View view = this.findViewById(R.id.msg_card_lastcomment_ll);
                View line = this.findViewById(R.id.main_line);
                if (showMainImage){
                    view.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                }
            }
        }finally{
            typedArray.recycle();
        }
    }

    public void buildWithData (MsgCard msgCard) {
        this.msgCard = msgCard;
        // 处理图片
        if (msgCard.getAttachments() != null
                && msgCard.getAttachments().size() > 0) {
            MsgCardImageLayout fr = (MsgCardImageLayout) this.findViewById(R.id.msg_card_image);
            if (fr != null) {
                fr.buildWithData(msgCard.getAttachments());
            }
        }

        //content
        if(msgCard.getContent() != null){
            contentTV.setText(msgCard.getContent());
        }

        //location
        if(msgCard.getLocationlabel() != null){
            locationTV.setText(msgCard.getLocationlabel());
        }

        //createDate
        if(msgCard.getCreatedate() != null){
            timeTV.setText(DateUtil.timeAgo(msgCard.getCreatedate()));
        }

        //Praisecount
            praiseNumTV.setText(String.valueOf(msgCard.getPraisecount()));

        //Sharecount
         fwNumTV.setText(String.valueOf(msgCard.getSharecount()));

        //comment
        commentNumTV.setText(String.valueOf(msgCard.getCommentcount()));
/*
        //lastComment
        lastCommentContentTV.setText(msgCard.getComment().);*/


    }

    public TextView getContentTV() {
        return contentTV;
    }

    public void setContentTV(TextView contentTV) {
        this.contentTV = contentTV;
    }

    public TextView getLocationTV() {
        return locationTV;
    }

    public void setLocationTV(TextView locationTV) {
        this.locationTV = locationTV;
    }

    public TextView getTimeTV() {
        return timeTV;
    }

    public void setTimeTV(TextView timeTV) {
        this.timeTV = timeTV;
    }

    public RelativeLayout getPraiseRL() {
        return praiseRL;
    }

    public void setPraiseRL(RelativeLayout praiseRL) {
        this.praiseRL = praiseRL;
    }

    public RelativeLayout getFwRL() {
        return fwRL;
    }

    public void setFwRL(RelativeLayout fwRL) {
        this.fwRL = fwRL;
    }

    public RelativeLayout getCommentRL() {
        return commentRL;
    }

    public void setCommentRL(RelativeLayout commentRL) {
        this.commentRL = commentRL;
    }

    public RelativeLayout getActionRL() {
        return actionRL;
    }

    public void setActionRL(RelativeLayout actionRL) {
        this.actionRL = actionRL;
    }

    public TextView getPraiseNumTV() {
        return praiseNumTV;
    }

    public void setPraiseNumTV(TextView praiseNumTV) {
        this.praiseNumTV = praiseNumTV;
    }

    public TextView getFwNumTV() {
        return fwNumTV;
    }

    public void setFwNumTV(TextView fwNumTV) {
        this.fwNumTV = fwNumTV;
    }

    public TextView getCommentNumTV() {
        return commentNumTV;
    }

    public void setCommentNumTV(TextView commentNumTV) {
        this.commentNumTV = commentNumTV;
    }

    public TextView getActionNumTV() {
        return actionNumTV;
    }

    public void setActionNumTV(TextView actionNumTV) {
        this.actionNumTV = actionNumTV;
    }

    public TextView getLastCommentContentTV() {
        return lastCommentContentTV;
    }

    public void setLastCommentContentTV(TextView lastCommentContentTV) {
        this.lastCommentContentTV = lastCommentContentTV;
    }

    public ImageView getPraiseIV() {
        return praiseIV;
    }

    public void setPraiseIV(ImageView praiseIV) {
        this.praiseIV = praiseIV;
    }
}
