package com.yun9.wservice.view.dynamic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/11.
 */
public class NewDynamicShareToItemLayout extends JupiterRelativeLayout {


    private TextView textView;

    public NewDynamicShareToItemLayout(Context context) {
        super(context);
    }

    public NewDynamicShareToItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewDynamicShareToItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_new_dynamic_share_to_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        textView = (TextView) findViewById(R.id.text_tv);
    }

    public TextView getTextView() {
        return textView;
    }

}
