package com.yun9.jupiter.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/5.
 */
public class BasicJupiterEditAdapter extends JupiterEditAdapter {

    private List<JupiterEditableView> itemList;

    public BasicJupiterEditAdapter(List<JupiterEditableView> itemList) {
        super();
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public JupiterEditableView getItemView(int position, View convertView, ViewGroup parent) {
        itemList.get(position).setEnabled(BasicJupiterEditAdapter.this.edit);
        return itemList.get(position);
    }
}
