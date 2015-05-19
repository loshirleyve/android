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
public class MsgCardDetailToolbarWidget extends JupiterRelativeLayout {

    private MsgCardDetailToolbarTabsWidget tabsWidget;

    private MsgCardDetailToolbarPanelsWidget panelsWidget;

    public MsgCardDetailToolbarWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        tabsWidget = (MsgCardDetailToolbarTabsWidget) this.findViewById(R.id.msg_card_detail_toolbar_tabs);
        panelsWidget = (MsgCardDetailToolbarPanelsWidget) this.findViewById(R.id.msg_card_detail_toolbar_panels);

        //监听tab被点击
        tabsWidget.setOnTabClickListener(new MsgCardDetailToolbarTabsWidget.TabOnClickListener() {
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
                if (panelsWidget.getVisibility() == View.VISIBLE) {
                    hidePanels();
                } else {
                    showPanels();
                }
            }
        });
    }

    /**
     * 显示动作面板
     */
    public void showPanels() {
        panelsWidget.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏动作面板
     */
    public void hidePanels() {
        panelsWidget.setVisibility(View.GONE);
    }
}
