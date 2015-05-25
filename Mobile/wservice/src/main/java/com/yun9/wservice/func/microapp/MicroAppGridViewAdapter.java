package com.yun9.wservice.func.microapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppGridViewAdapter extends JupiterAdapter {

    @ViewInject(id = R.id.textView)
    private TextView textView;

    private Context mContext;
    private List<View> mListViews;

    public MicroAppGridViewAdapter(List<View> listViews){
        this.mListViews = listViews;
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public Object getItem(int position) {
        return mListViews.get(position);
    }
/*    @Override
    public Object getItem(int position) {
        return position;
    }*/
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*          View view = this.mListViews.get(position);
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.widget_micro_app_gridview_item, null);
        }
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.gridViewItem);
        linearLayout.setTag(view);
        convertView.setTag(view);
        return convertView;*/
  /*    return mListViews.get(position);*/
        View view = this.mListViews.get(position);
        return view;

    }
}
