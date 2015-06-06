package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterEditItem extends JupiterRelativeLayout {

    private JupiterRowStyleTitleLayout rowStyleTitleLayout;

    private ListView listView;

    public JupiterEditItem(Context context) {
        super(context);
    }

    public JupiterEditItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_edit_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        rowStyleTitleLayout = (JupiterRowStyleTitleLayout) this.findViewById(R.id.title_ll);
        listView = (ListView) this.findViewById(R.id.listview);
    }

    public void setAdapter(JupiterEditAdapter adapter) {
        listView.setAdapter(adapter);
    }

    public void edit(boolean edit) {
        JupiterEditAdapter adapter = (JupiterEditAdapter) this.listView.getAdapter();
        adapter.edit(edit);
    }

    public JupiterRowStyleTitleLayout getRowStyleTitleLayout() {
        return rowStyleTitleLayout;
    }

    public void setRowStyleTitleLayout(JupiterRowStyleTitleLayout rowStyleTitleLayout) {
        this.rowStyleTitleLayout = rowStyleTitleLayout;
    }

    public void setListViewHeightBasedOnChildren() {
        if (listView == null ||
                listView.getAdapter() == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
