package com.yun9.mobile.framework.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.model.AttendanceState;
import com.yun9.mobile.framework.model.ModelUserCheckinginInfo;

public class MySigninListAdapter extends BaseAdapter
{

    private Context context;
    private List<ModelUserCheckinginInfo> list;

    public MySigninListAdapter(Context context, List<ModelUserCheckinginInfo> list)
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
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        final ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_my_sign_in_listview, null);
            holder.tv_job_time = (TextView) view.findViewById(R.id.tv_job_time);
            holder.tv_job_job = (TextView) view.findViewById(R.id.tv_job_job);
            holder.tv_signed_addr = (TextView) view.findViewById(R.id.tv_signed_addr);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        if (list != null && list.size() > 0)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            String workdate = format.format(new Date(Long.valueOf(list.get(i).getWorkdate())));
            String checkdate = format1.format(new Date(list.get(i).getCheckdatetime()));
            holder.tv_job_time.setText(workdate);
            holder.tv_job_job.setText(list.get(i).getShiftname() + " 打卡时间:"+checkdate
            		+ " " + AttendanceState.getValueByName(list.get(i).getState()));
            if (list.get(i).isChecked())
            {
                holder.tv_signed_addr.setText(list.get(i).getChecklocationlabel());
            }
        }
        return view;
    }

    class ViewHolder
    {
        TextView tv_job_time,tv_job_job,tv_signed_addr;
    }
}
