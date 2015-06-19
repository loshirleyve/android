package com.yun9.jupiter.widget.paging.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;


public class LoadingView extends LinearLayout {

    private LinearLayout loadingView;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_view, this);
        loadingView = (LinearLayout) findViewById(R.id.loading_view);
    }

    public void showLoading(boolean show) {
        if (show) {
            loadingView.setVisibility(View.VISIBLE);
        } else {
            loadingView.setVisibility(View.GONE);
        }
    }

}
