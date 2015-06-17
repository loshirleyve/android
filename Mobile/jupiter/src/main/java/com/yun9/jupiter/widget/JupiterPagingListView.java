package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;


public class JupiterPagingListView extends ListView {

    private boolean isLoading;
    private boolean hasMoreItems;
    private Pagingable pagingableListener;
    private JupiterLoadMoreView loadingView;

    public JupiterPagingListView(Context context) {
        super(context);
        init();
    }

    public JupiterPagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JupiterPagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPagingableListener(Pagingable pagingableListener) {
        this.pagingableListener = pagingableListener;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        if (!this.hasMoreItems) {
            removeFooterView(loadingView);
        } else if (findViewById(R.id.loadmore_view) == null) {
            addFooterView(loadingView);
        }
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }

    public void onFinishLoading(boolean hasMoreItems) {
        setHasMoreItems(hasMoreItems);
        setIsLoading(false);
    }

    private void init() {
        isLoading = false;
        loadingView = new JupiterLoadMoreView(getContext());
        addFooterView(loadingView);
        loadingView.getLoadmoreLL().setOnClickListener(onClickListener);
    }

    public void loadComplete() {
        isLoading = false;
        loadingView.getLoadmoreLL().setVisibility(View.VISIBLE);
        loadingView.getLoadingLL().setVisibility(View.GONE);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(pagingableListener)) {
                isLoading = true;
                loadingView.getLoadmoreLL().setVisibility(View.GONE);
                loadingView.getLoadingLL().setVisibility(View.VISIBLE);
                pagingableListener.onLoadMoreItems();
            }
        }
    };


    public interface Pagingable {
        void onLoadMoreItems();
    }
}
