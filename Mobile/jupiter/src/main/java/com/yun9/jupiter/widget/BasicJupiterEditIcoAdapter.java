package com.yun9.jupiter.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/5.
 */
public class BasicJupiterEditIcoAdapter extends JupiterEditIcoAdapter{

    private List<JupiterTextIco> itemList;

    private boolean edit;

    public BasicJupiterEditIcoAdapter(List<JupiterTextIco> itemList) {
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
    public JupiterTextIco getItemView(int position, View convertView, ViewGroup parent) {
        itemList.get(position).setEnabled(edit);
        return itemList.get(position);
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
