package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.form.model.UserFormCellBean;
import com.yun9.jupiter.view.JupiterBadgeView;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/5/30.
 */
public class UserFormCell extends FormCell{

    public static final String PARAM_KEY_TYPE = "type";
    public static final String PARAM_KEY_VALUE = "value";

    private JupiterRowStyleTitleLayout titleView;

    private JupiterGridView userGridView;

    private UserFormCellBean cellBean;

    private List<Map<String,String>> uodMaps;

    private Context context;

    private BaseAdapter pagerAdapter;

    private boolean isEdit;

    public UserFormCell(FormCellBean cellBean) {
        super(cellBean);
        this.cellBean = (UserFormCellBean) cellBean;
    }

    @Override
    public View getCellView(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.form_cell_user, null);
        titleView = (JupiterRowStyleTitleLayout) rootView.findViewById(R.id.title);
        userGridView = (JupiterGridView) rootView.findViewById(R.id.userGridView);
        titleView.getTitleTV().setText(cellBean.getLabel());
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceUserOrDept();
            }
        });
        uodMaps = new ArrayList<>();
        this.setupAdapter();
        this.restore();
        return rootView;
    }

    private void setupAdapter() {
        pagerAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return uodMaps.size() +1;
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
                UserFormCellItemWidget widget;
                if (convertView != null) {
                    widget = (UserFormCellItemWidget) convertView;
                    if (position < uodMaps.size()
                            && widget.getImageId().equals(uodMaps.get(position).get(PARAM_KEY_VALUE))){
                        return widget;
                    }
                }
                widget = new UserFormCellItemWidget(context);
                if (position == uodMaps.size()) {
                    widget.buildWithData("drawable://" + R.drawable.add_user, "添加");
                    widget.getItemName().setTextColor(UserFormCell.this.context.getResources().getColor(R.color.red));
                    widget.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            choiceUserOrDept();
                        }
                    });
                    widget.setEnabled(isEdit);
                    return widget;
                }
                Map<String,String> params = uodMaps.get(position);
                widget.buildWithData(params.get(PARAM_KEY_VALUE),"成员名称");
                appendBadges(position,widget.getContainer());
                return widget;
            }
        };
        this.userGridView.setAdapter(pagerAdapter);
    }

    private void restore() {
        if (cellBean.getValue() != null) {
            uodMaps = (List<Map<String, String>>) cellBean.getValue();
        }
        this.reloadDBAndRefreshAdapter();
    }

    private void reloadDBAndRefreshAdapter() {
        // 根据uodMaps现有的数据，判断缓冲里面有没有存在相应的数据，没有则重新加载
        refreshAdapter();
    }

    private void refreshAdapter() {
        pagerAdapter.notifyDataSetInvalidated();
    }

    private void appendBadges(final int position,View view) {
            JupiterBadgeView badgeView = new JupiterBadgeView(context, view);
            badgeView.setBadgePosition(JupiterBadgeView.POSITION_TOP_RIGHT_EDGE);
            badgeView.setBackgroundResource(R.drawable.icn_delete);
            badgeView.setBadgeSize(20, 20);
            badgeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(position);
                }
            });
            if (isEdit){
                badgeView.show();
            }
    }

    /**
     * 删除成员
     * @param position 成员下标
     */
    private void removeView(int position) {
        uodMaps.remove(position);
        this.refreshAdapter();
    }

    /**
     * 激活选择用户Activity
     */
    private void choiceUserOrDept() {
        Map<String,String> map = new HashMap<>();
        map.put(PARAM_KEY_TYPE,UserFormCellBean.MODE.USER+"");
        map.put(PARAM_KEY_VALUE,"drawable://"+R.drawable.user_head);
       uodMaps.add(map);
        this.reloadDBAndRefreshAdapter();
    }

    @Override
    public void edit(boolean edit) {
        this.isEdit = edit;
        titleView.setEnabled(edit);
        uodMaps = new ArrayList<>(uodMaps);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public Object getValue() {
        return uodMaps;
    }

    @Override
    public FormCellBean getFormCellBean() {
        return cellBean;
    }
}
