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

    private  boolean isFirstClickOfActionLayout = true;// 是否第一次点击动作按钮，默认true

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
        initEvent();

        // 设置点击事件
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == praiseLayout.getId()) {
                    onTabClickListener.onPraiseClick(praiseLayout);
                } else if (v.getId() == forwardLayout.getId()) {
                    onTabClickListener.onForwardClick(forwardLayout);
                } else if (v.getId() == commentLayout.getId()) {
                    onTabClickListener.onCommentClick(commentLayout);
                } else if (v.getId() == actionLayout.getId()) {
                    // 改变图标
                    ImageView imageView = (ImageView) actionLayout.findViewById(R.id.action_iv);
                    if (isFirstClickOfActionLayout) {
                        isFirstClickOfActionLayout = false;
                        imageView.setImageResource(R.drawable.action2);
                    } else {
                        isFirstClickOfActionLayout = true;
                        imageView.setImageResource(R.drawable.action1);
                    }
                    onTabClickListener.onActionClick(actionLayout);
                }
            }
        };
        praiseLayout.setOnClickListener(onClickListener);
        forwardLayout.setOnClickListener(onClickListener);
        commentLayout.setOnClickListener(onClickListener);
        actionLayout.setOnClickListener(onClickListener);
    }

    // 初始化tab点击监听器，默认什么都不执行
    private void initEvent() {
        this.onTabClickListener = new TabOnClickListener() {
            @Override
            public void onPraiseClick(RelativeLayout relativeLayout) {
            }

            @Override
            public void onForwardClick(RelativeLayout relativeLayout) {
            }

            @Override
            public void onCommentClick(RelativeLayout relativeLayout) {
            }

            @Override
            public void onActionClick(RelativeLayout relativeLayout) {
            }
        };
    }

    /**
     * 点击点赞按钮
     */
    public void clickPraiseBtn() {
        praiseLayout.callOnClick();
    }

    /**
     * 点击转发按钮
     */
    public void clickForwardBtn() {
        forwardLayout.callOnClick();
    }

    /**
     * 点击评论按钮
     */
    public void clickCommentBtn() {
        commentLayout.callOnClick();
    }

    /**
     * 点击动作按钮
     */
    public void clickActionBtn() {
        actionLayout.callOnClick();
    }

    /**
     * 接口定义，4个按钮被点击时的不同处理回调
     */
    public interface TabOnClickListener {
        public void onPraiseClick(RelativeLayout relativeLayout);
        public void onForwardClick(RelativeLayout relativeLayout);
        public void onCommentClick(RelativeLayout relativeLayout);
        public void onActionClick(RelativeLayout relativeLayout);
    }
}
