package com.yun9.wservice.view.msgcard;

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
public class MsgCardDetailToolbarTabsWidget extends JupiterRelativeLayout{

    private TabOnClickListener onTabClickListener;

    private RelativeLayout praiseLayout;
    private RelativeLayout forwardLayout;
    private RelativeLayout commentLayout;
    private RelativeLayout actionLayout;

    public MsgCardDetailToolbarTabsWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarTabsWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarTabsWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void  setOnTabClickListener (TabOnClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_tabs;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        praiseLayout = (RelativeLayout) this.findViewById(R.id.praise_rl);
        forwardLayout= (RelativeLayout) this.findViewById(R.id.fw_rl);
        commentLayout = (RelativeLayout) this.findViewById(R.id.comm_rl);
        actionLayout = (RelativeLayout) this.findViewById(R.id.action_rl);

        // 设置点击事件
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 没设定事件监听，什么也不操作
                if (onTabClickListener == null) {
                    return;
                }
                if (v.getId() == praiseLayout.getId()) {
                    onTabClickListener.onPraiseClick(praiseLayout);
                } else if (v.getId() == forwardLayout.getId()) {
                    onTabClickListener.onForwardClick(forwardLayout);
                } else if (v.getId() == commentLayout.getId()) {
                    onTabClickListener.onCommentClick(commentLayout);
                } else if (v.getId() == actionLayout.getId()) {
                    onTabClickListener.onActionClick(actionLayout);
                }
            }
        };
        praiseLayout.setOnClickListener(onClickListener);
        forwardLayout.setOnClickListener(onClickListener);
        commentLayout.setOnClickListener(onClickListener);
        actionLayout.setOnClickListener(onClickListener);
    }

    public interface TabOnClickListener {
        public void onPraiseClick(RelativeLayout relativeLayout);
        public void onForwardClick(RelativeLayout relativeLayout);
        public void onCommentClick(RelativeLayout relativeLayout);
        public void onActionClick(RelativeLayout relativeLayout);
    }
}
