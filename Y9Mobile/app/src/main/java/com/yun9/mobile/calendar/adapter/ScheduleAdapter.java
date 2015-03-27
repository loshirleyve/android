package com.yun9.mobile.calendar.adapter;

import java.util.List;
import com.yun9.mobile.R;
import com.yun9.mobile.calendar.entity.ShiftInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends BaseAdapter {
	
	private Context context = null;
	private List<ShiftInfo> list = null;
	
	public ScheduleAdapter(Context context, List<ShiftInfo> list) {
		this.context = context;
        this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 通过ViewHolder做一些优化
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.schedule_detail_item, null);
            viewHolder.label = (TextView) convertView.findViewById(R.id.scheduleDetail_Label_Text);
            viewHolder.workTime = (TextView) convertView.findViewById(R.id.scheduleDetail_WorkTime_Text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
		
        // 根据数据源设置单元格显示的内容
        viewHolder.label.setText(list.get(position).getLabel());
        viewHolder.workTime.setText(list.get(position).getWorkTime());
		
		return convertView;
	}

	private static class ViewHolder {
        TextView label;
        TextView workTime;
    }
	
}
