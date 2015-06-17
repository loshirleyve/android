package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/5.
 */
public class JupiterLoadMoreView extends LinearLayout {

    private LinearLayout loadingLL;

    private LinearLayout loadmoreLL;

    public JupiterLoadMoreView(Context context) {
        super(context);
        init();
    }

    public JupiterLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loadmore_view, this);

        loadingLL = (LinearLayout) this.findViewById(R.id.loading_ll);
        loadmoreLL = (LinearLayout) this.findViewById(R.id.loadmore_ll);
    }

    public LinearLayout getLoadingLL() {
        return loadingLL;
    }

    public LinearLayout getLoadmoreLL() {
        return loadmoreLL;
    }
}
