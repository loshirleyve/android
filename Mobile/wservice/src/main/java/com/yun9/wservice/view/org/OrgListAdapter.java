package com.yun9.wservice.view.org;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;

import java.util.List;

/**
 * Created by Leon on 15/6/1.
 */
public class OrgListAdapter extends JupiterAdapter {

    private Context mContext;

    private List<OrgListBean> orgListBeans;

    private boolean selectMode;

    public OrgListAdapter(Context context, List<OrgListBean> orgListBeans) {
        this.mContext = context;
        this.orgListBeans = orgListBeans;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNull(orgListBeans)) {
            return orgListBeans.size();
        } else {
            return 0;
        }
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
    }

    @Override
    public Object getItem(int position) {
        return orgListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrgListBean orgListBean = orgListBeans.get(position);
        JupiterRowStyleSutitleLayout item = null;

        if (!AssertValue.isNotNull(convertView)) {
            item = new JupiterRowStyleSutitleLayout(mContext);
            item.setShowTime(false);
            item.setShowArrow(false);
            item.getMainIV().setImageResource(R.drawable.user_group);
            item.setOnSelectListener(new OnSelectListener() {
                @Override
                public void onSelect(View view, boolean mode) {
                    OrgListBean tempBean = (OrgListBean) view.getTag();
                    tempBean.setSelected(mode);
                }
            });
        } else {
            item = (JupiterRowStyleSutitleLayout) convertView;
        }

        item.setSelectMode(selectMode);
        item.getTitleTV().setText(orgListBean.getName());
        item.getSutitleTv().setText(orgListBean.getUserNames());
        item.select(orgListBean.isSelected());
        item.setTag(orgListBean);

        return item;
    }
}
