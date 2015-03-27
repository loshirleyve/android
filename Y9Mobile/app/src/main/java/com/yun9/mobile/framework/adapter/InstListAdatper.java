package com.yun9.mobile.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.Inst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstListAdatper extends BaseAdapter
{
    private Context context;
    private List<Inst> list;
    private int position;
    private Map<Integer,View> map = new HashMap<Integer, View>();
    private Map<Integer,Integer> explain = new HashMap<Integer, Integer>();

    public InstListAdatper(Context context, List<Inst> list,int position)
    {
        this.context = context;
        this.list = list;
        this.position = position;
        for (int i = 0;i < list.size();i++)
        {
            explain.put(i,0);
        }
        explain.put(position,1);
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
        View mView;
        ViewHolder holder;
        if (map.get(i) == null)
        {
            holder = new ViewHolder();
            mView = LayoutInflater.from(context).inflate(R.layout.adapter_inst_list, null);
            holder.textView = (TextView) mView.findViewById(R.id.tv_inst);
            holder.view = mView.findViewById(R.id.view_inst);
            holder.view.setVisibility(View.GONE);
            map.put(i,mView);
            mView.setTag(holder);
        }else {
            mView = map.get(i);
            holder = (ViewHolder) mView.getTag();
        }

        if (list != null && list.size() > 0)
        {
            if (explain.get(i) == 1)
            {
                holder.view.setVisibility(View.VISIBLE);
                holder.textView.setText(list.get(i).getName());
                holder.textView.setTextColor(context.getResources().getColor(R.color.red));
            }else
            {
                holder.view.setVisibility(View.GONE);
                holder.textView.setText(list.get(i).getName());
                holder.textView.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
        return mView;
    }

    class ViewHolder
    {
        TextView textView;
        View view;
    }
}
