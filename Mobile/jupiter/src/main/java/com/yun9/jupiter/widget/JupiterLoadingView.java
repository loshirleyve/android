package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/5.
 */
public class JupiterLoadingView extends LinearLayout {
    public JupiterLoadingView(Context context) {
        super(context);
        init();
    }

    public JupiterLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_view, this);
    }
}
