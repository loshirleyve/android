package com.yun9.wservice.view.microapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterIco;
import com.yun9.wservice.model.MicroAppBean;

import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppGridViewAdapter extends JupiterAdapter {

    private List<MicroAppBean> microAppBeans;

    private Context mContext;

    private View.OnClickListener onClickListener;

    public MicroAppGridViewAdapter(Context context,List<MicroAppBean> microAppBeans){
        this.microAppBeans = microAppBeans;
        this.mContext = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return microAppBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return microAppBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JupiterIco jupiterIco = null;

        if (AssertValue.isNotNull(convertView)){
            jupiterIco = (JupiterIco) convertView;
        }else{
            jupiterIco = new JupiterIco(mContext);
            if (AssertValue.isNotNull(onClickListener)) {
                jupiterIco.setOnClickListener(onClickListener);
            }
        }

        jupiterIco



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
        return  view;

    }
}
