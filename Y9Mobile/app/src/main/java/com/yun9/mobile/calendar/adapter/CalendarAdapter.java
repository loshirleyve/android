package com.yun9.mobile.calendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import com.yun9.mobile.R;
import com.yun9.mobile.calendar.entity.DateInfo;

/**
 * 日历GridView适配器
 * Created by Kass on 2014/12/8.
 */
public class CalendarAdapter extends BaseAdapter {

    private List<DateInfo> list = null;
    private Context context = null;
    private int selectPosition = -1;  // 标记选择的位置
    private int todayPosition = -1;  // 标记当天的位置

    public CalendarAdapter(Context context, List<DateInfo> list) {
        this.context = context;
        this.list = list;
    }

    public List<DateInfo> getList() {
        return list;
    }

    /**
     * 设置选择的位置
     */
    public void setSelectPosition(int position) {
    	selectPosition = position;
    }

    /**
     * 设置今天的位置
     */
    public void setTodayPosition(int position) {
        todayPosition = position;
    }
    
    /**
     * 获得选取的日期
     */
    public int getDay(int position) {
        return list.get(position).getDate();
    }
    
    /**
     * 选择的如果是本月的日期才会有事件监听
     */
    public boolean isListener(int position) {
        return list.get(position).isThisMonth();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
	@Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 通过ViewHolder做一些优化
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.schedule_item, null);
            viewHolder.solar = (TextView) view.findViewById(R.id.scheduleItem_Date_Text);
            viewHolder.lunar = (TextView) view.findViewById(R.id.scheduleItem_Lunar_Text);
            viewHolder.date = (LinearLayout) view.findViewById(R.id.scheduleItem_Date_LL);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 根据数据源设置单元格的字体颜色、背景等
        viewHolder.solar.setText(String.valueOf(list.get(i).getDate()));
        viewHolder.lunar.setText(list.get(i).getLunarDate());
        view.setBackgroundResource(R.drawable.item_border);
        if (selectPosition == i) {
        	viewHolder.date.setBackgroundColor(Color.RED);
            viewHolder.solar.setTextColor(Color.WHITE);
            viewHolder.lunar.setTextColor(Color.WHITE);
        }
        else {
        	viewHolder.date.setBackgroundResource(R.drawable.item_border);
            viewHolder.solar.setTextColor(Color.BLACK);
            viewHolder.lunar.setTextColor(Color.GRAY);
            if (list.get(i).isWeekend()) {
                viewHolder.solar.setTextColor(Color.rgb(255, 97, 0));
            }
            if (list.get(i).isHoliday()) {
                viewHolder.lunar.setTextColor(Color.BLUE);
            }
            if (list.get(i).isThisMonth() == false) {
                viewHolder.solar.setTextColor(Color.rgb(210, 210, 210));
            }
            if (todayPosition == i) {
            	viewHolder.solar.setTextColor(Color.RED);
            }
        }

        return view;
    }

    private static class ViewHolder {
        TextView solar;
        TextView lunar;
        LinearLayout date;
    }

}
