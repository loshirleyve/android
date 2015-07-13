package com.yun9.wservice.view.org;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;

import java.util.List;

/**
 * Created by Leon on 15/5/30.
 */
public class OrgCompositeListAdapter extends BaseAdapter {

    private List<OrgCompositeUserListBean> mOrgCompositeUserListBeans;

    private Context mContext;

    private OnSelectListener onSelectListener;

    private View.OnClickListener onClickForDetail;

    private boolean selectMode;

    public OrgCompositeListAdapter(Context context, List<OrgCompositeUserListBean> orgCompositeUserListBeans, boolean selectMode) {
        this.mOrgCompositeUserListBeans = orgCompositeUserListBeans;
        this.mContext = context;
        this.selectMode = selectMode;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
    }

    public View.OnClickListener getOnClickForDetail() {
        return onClickForDetail;
    }

    public void setOnClickForDetail(View.OnClickListener onClickForDetail) {
        this.onClickForDetail = onClickForDetail;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(mOrgCompositeUserListBeans)) {
            return mOrgCompositeUserListBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mOrgCompositeUserListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JupiterRowStyleSutitleLayout tempView = null;
        OrgCompositeUserListBean orgCompositeUserListBean = mOrgCompositeUserListBeans.get(position);
        if (AssertValue.isNotNull(convertView)) {
            tempView = (JupiterRowStyleSutitleLayout) convertView;
        } else {
            tempView = new JupiterRowStyleSutitleLayout(mContext);
        }

        if (this.selectMode) {
            tempView.setSelectMode(true);
            tempView.setOnSelectListener(onSelectListener);
            tempView.select(orgCompositeUserListBean.isSelected());
        } else {
            tempView.setSelectMode(false);
            tempView.setOnClickListener(onClickForDetail);
        }

        tempView.getTitleTV().setText(orgCompositeUserListBean.getUser().getName());
        tempView.getSutitleTv().setText(orgCompositeUserListBean.getUser().getSignature());
        tempView.getTimeTv().setVisibility(View.GONE);
        tempView.getArrowRightIV().setVisibility(View.GONE);
        tempView.setTag(orgCompositeUserListBean);

        return tempView;
    }
}
