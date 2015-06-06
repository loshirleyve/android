package com.yun9.jupiter.widget;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangbinglong on 15/6/3.
 */
public abstract class JupiterEditAdapter extends JupiterAdapter{

    protected boolean edit;

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        JupiterEditableView item = getItemView(position,convertView,parent);
        item.edit(edit);
        return item;
    }

    public abstract JupiterEditableView getItemView(int position, View convertView, ViewGroup parent);

    public void edit(boolean edit) {
        this.edit = edit;
        this.notifyDataSetChanged();
    }

}
