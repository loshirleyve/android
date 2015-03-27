package com.yun9.mobile.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.ModelUserCheckinginInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class MySignListAdapter extends BaseAdapter
{
    private Context context;
    private List<ModelUserCheckinginInfo> list;

    public MySignListAdapter(Context context, List<ModelUserCheckinginInfo> list)
    {
        this.context = context;
        this.list = list;
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
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_sign_listview, null);
            holder.tv_job_time = (TextView) view.findViewById(R.id.tv_job_time);
            holder.tv_job_job = (TextView) view.findViewById(R.id.tv_job_job);
            holder.tv_signed_time = (TextView) view.findViewById(R.id.tv_signed_time);
            holder.tv_signed_addr = (TextView) view.findViewById(R.id.tv_signed_addr);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        if (list != null && list.size() > 0)
        {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String workdate = format.format(list.get(i).getWorkdate());
            holder.tv_job_time.setText("上班时间：" + workdate);
            holder.tv_job_job.setText("班次：" + list.get(i).getShiftname());
            if (list.get(i).getCheckdatetime() != 0 && list.get(i).getChecklocationlabel() != null)
            {
                String actualcheckdatetime = format.format(list.get(i).getCheckdatetime());
                holder.tv_signed_time.setText("签到时间：" + actualcheckdatetime);
                holder.tv_signed_addr.setText("签到地点：" + list.get(i).getChecklocationlabel());
            } else
            {
                holder.tv_signed_time.setText("签到时间：没有打卡记录");
                holder.tv_signed_addr.setText("签到地点：没有打卡记录");
            }
        }
        return view;
    }

    class ViewHolder
    {
        TextView tv_job_time,tv_job_job,tv_signed_time,tv_signed_addr;
    }
}
