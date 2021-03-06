package com.yun9.wservice.view.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.model.OrderInfo;
import com.yun9.wservice.model.WorkorderDto;

import java.util.List;

/**
 * Created by rxy on 15/8/24.
 */
public class OrderListSubItemAdapter extends JupiterAdapter {

    private List<WorkorderDto> workorderDtos;

    private Context mcontext;

    public OrderListSubItemAdapter(List<WorkorderDto> workorderDtos, Context mcontext) {
        this.workorderDtos = workorderDtos;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(workorderDtos)) {
            return workorderDtos.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return workorderDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WidgetOrderListSubItem widgetOrderListSubItem = null;
        WorkorderDto workorderDto = workorderDtos.get(position);
        if (convertView == null) {
            widgetOrderListSubItem = new WidgetOrderListSubItem(mcontext);
            convertView = widgetOrderListSubItem;
        } else {
            widgetOrderListSubItem = (WidgetOrderListSubItem) convertView;
        }

        widgetOrderListSubItem.getWorkorderitemdescr().setText(workorderDto.getDescr());
        widgetOrderListSubItem.getWorkorderitemname().setText(workorderDto.getInserviceName());
        widgetOrderListSubItem.getWorkorderitemnums().setText(workorderDto.getCompleteNum() + "/" + workorderDto.getAllNum());
        widgetOrderListSubItem.setTag(workorderDto);
        return widgetOrderListSubItem;
    }
};