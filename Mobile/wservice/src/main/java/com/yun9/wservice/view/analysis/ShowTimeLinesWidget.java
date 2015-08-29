package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterLinearLayout;
import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.DateSection;

import java.util.List;

/**
 * Created by huangbinglong on 15/8/28.
 */
public class ShowTimeLinesWidget extends JupiterLinearLayout {

    private JupiterListView timeLineListView;

    private List<DateSection> timeLineList;

    private JupiterAdapter timeLineAdapter;

    private SelectedTimeLineCallback callback;

    public ShowTimeLinesWidget(Context context) {
        super(context);
    }

    public ShowTimeLinesWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowTimeLinesWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_show_time_line;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        timeLineListView = (JupiterListView) this.findViewById(R.id.show_time_line_list);
    }

    public void buildWithData(List<DateSection> timeLineList) {
        this.timeLineList = timeLineList;
        if (timeLineAdapter !=null){
            timeLineAdapter.notifyDataSetChanged();
        } else {
            timeLineAdapter = createAdapter();
            timeLineListView.setAdapter(timeLineAdapter);
        }
    }

    private JupiterAdapter createAdapter() {
        return new JupiterAdapter() {
            @Override
            public int getCount() {
                if (timeLineList != null){
                    return timeLineList.size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final DateSection timeLine = timeLineList.get(position);
                TimeLineItemWidget widget = null;
                if (convertView == null){
                    widget = new TimeLineItemWidget(getContext());
                    widget.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DateSection line = (DateSection) v.getTag();
                            if (callback != null){
                                callback.callback(line);
                            }
                        }
                    });
                    convertView = widget;
                } else {
                    widget = (TimeLineItemWidget) convertView;
                }
                widget.getLable().setText(timeLine.getLabel());
                widget.setTag(timeLine);
                return convertView;
            }
        };
    }

    public SelectedTimeLineCallback getCallback() {
        return callback;
    }

    public ShowTimeLinesWidget setCallback(SelectedTimeLineCallback callback) {
        this.callback = callback;
        return this;
    }
}
