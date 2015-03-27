package com.yun9.mobile.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.Topic;

import java.util.Collection;
import java.util.List;

/**
 * Created by Zhang on 2014/11/19.
 */
public class TopicAdapter extends BaseAdapter
{
    private List<Topic> list;
    private Context context;

    public TopicAdapter(List<Topic> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_topic,null);
            holder.iv_topic_head = (ImageView) view.findViewById(R.id.iv_topic_headPic);
            holder.iv_topic_table = (ImageView) view.findViewById(R.id.iv_topic_lable);
            holder.tv_topic_name = (TextView) view.findViewById(R.id.tv_topic_name);
            holder.tv_topic_explain = (TextView) view.findViewById(R.id.tv_topic_explain);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        
        if (list != null && list.size() > 0)
        {
            holder.tv_topic_name.setText(list.get(i).getName());
            holder.tv_topic_explain.setText(list.get(i).getType());
        }
        return view;
    }

    class ViewHolder
    {
        ImageView iv_topic_head,iv_topic_table;
        TextView tv_topic_name,tv_topic_explain;
    }
}
