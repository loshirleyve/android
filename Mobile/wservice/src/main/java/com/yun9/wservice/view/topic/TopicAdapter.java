package com.yun9.wservice.view.topic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.model.TopicBean;

import java.util.List;

/**
 * Created by Leon on 15/6/12.
 */
public class TopicAdapter extends JupiterAdapter {

    private List<TopicBean> mTopicBeans;

    private Context mContext;


    public TopicAdapter(Context context, List<TopicBean> topicBeans) {
        this.mContext = context;
        this.mTopicBeans = topicBeans;
    }

    @Override
    public int getCount() {
        return this.mTopicBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mTopicBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = null;
        TopicBean topicBean = mTopicBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        } else {
            jupiterRowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);

            jupiterRowStyleSutitleLayout.setShowTime(false);
            //jupiterRowStyleSutitleLayout.setSelectMode(false);
            jupiterRowStyleSutitleLayout.setShowMainImage(false);
            jupiterRowStyleSutitleLayout.setShowSutitleText(true);
        }

        jupiterRowStyleSutitleLayout.setTag(topicBean);
        jupiterRowStyleSutitleLayout.getTitleTV().setText(topicBean.getName());
        jupiterRowStyleSutitleLayout.getSutitleTv().setText("热度 "+topicBean.getHotnum());

        return jupiterRowStyleSutitleLayout;
    }
}
