package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.view.msgcard.model.MsgCardDetailToolbarActionItem;

/**
 * Created by huangbinglong on 15/5/20.
 */
public class MsgCardDetailToolbarPanelsPageItemWidget extends JupiterRelativeLayout {

    private ImageView imageView;
    private TextView textView;

    public MsgCardDetailToolbarPanelsPageItemWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarPanelsPageItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarPanelsPageItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(MsgCardDetailToolbarActionItem item) {
        imageView.setImageResource(item.getPic());
        textView.setText(item.getTitle());
        if (item.getOnClickListener() != null) {
            this.setOnClickListener(item.getOnClickListener());
        }
    }


    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels_page_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        imageView = (ImageView) this.findViewById(R.id.msg_card_action_image);
        textView = (TextView) this.findViewById(R.id.msg_card_action_title);
    }
}
