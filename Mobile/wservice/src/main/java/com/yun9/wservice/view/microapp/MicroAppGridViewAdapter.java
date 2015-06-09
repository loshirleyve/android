package com.yun9.wservice.view.microapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.navigation.NavigationBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTextIco;

import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppGridViewAdapter extends JupiterAdapter {

    private List<NavigationBean> microAppBeans;

    private Context mContext;

    private View.OnClickListener onClickListener;

    public MicroAppGridViewAdapter(Context context,List<NavigationBean> microAppBeans){
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

        JupiterTextIco jupiterTextIco = null;
        NavigationBean microAppBean = microAppBeans.get(position);

        if (AssertValue.isNotNull(convertView)){
            jupiterTextIco = (JupiterTextIco) convertView;
        }else{
            jupiterTextIco = new JupiterTextIco(mContext);
            if (AssertValue.isNotNull(onClickListener)) {
                jupiterTextIco.setOnClickListener(onClickListener);
            }
        }

        jupiterTextIco.setTitle(microAppBean.getName());
        jupiterTextIco.setTag(microAppBean);

        return jupiterTextIco;
    }
}
