package com.yun9.jupiter.widget.paging.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.widget.paging.LoadingView;

import java.util.List;


public class PagingListView extends ListView {

    private boolean isLoading;
    private boolean isFling;
    private boolean hasMoreItems = true;
    private Pagingable pagingableListener;
    private LoadingView loadingView;
    private OnScrollListener onScrollListener;

    public PagingListView(Context context) {
        super(context);
        init();
    }

    public PagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagingListView(Context context, AttributeSet attrs, int defStyle) {
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
        }
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }

    public void onFinishLoading(boolean hasMoreItems) {
        if (this.hasMoreItems) {
            setHasMoreItems(hasMoreItems);
            setIsLoading(false);
            loadingView.showLoading(false);
        }
    }

    private void init() {
        isLoading = false;
        loadingView = new LoadingView(getContext());
        addFooterView(loadingView);
        loadingView.showLoading(false);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                int lastVisibleItem = firstVisibleItem + visibleItemCount;
                System.out.println("lastVisibleItem: "+lastVisibleItem);
                System.out.println("totalItemCount: "+totalItemCount);
                System.out.println("visibleItemCount: "+visibleItemCount);
                System.out.println("firstVisibleItem: "+firstVisibleItem);
                if (!isLoading && hasMoreItems && (lastVisibleItem == totalItemCount) && isFling) {
                    if (pagingableListener != null) {
                        isLoading = true;
                        loadingView.showLoading(true);
                        pagingableListener.onLoadMoreItems();
                    }

                }
            }
        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    isFling = true;
                } else {
                    isFling = false;
                }
                System.out.println("isFling: "+isFling);
                return false;
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    public interface Pagingable {
        void onLoadMoreItems();
    }
}
