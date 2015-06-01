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

    private View.OnClickListener hrOnClickListener;

    private View.OnClickListener groupOnClickListener;

    private View.OnClickListener myselfOnClickListener;

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

    public void setHrOnClickListener(View.OnClickListener hrOnClickListener) {
        this.hrOnClickListener = hrOnClickListener;
    }

    public void setGroupOnClickListener(View.OnClickListener groupOnClickListener) {
        this.groupOnClickListener = groupOnClickListener;
    }

    public void setMyselfOnClickListener(View.OnClickListener myselfOnClickListener) {
        this.myselfOnClickListener = myselfOnClickListener;
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

        //由于会根据不同的类型决定显示不同的view,所以不能重用convertView,必须每次创建新的view

        OrgCompositeUserListBean orgCompositeUserListBean = mOrgCompositeUserListBeans.get(position);
        if (orgCompositeUserListBean.isTop()) {
            OrgCompositeTopWidget orgCompositeTopWidget  = new OrgCompositeTopWidget(mContext);
            if (AssertValue.isNotNull(groupOnClickListener)) {
                orgCompositeTopWidget.getOrgGroupLL().setOnClickListener(groupOnClickListener);
            }
            if (AssertValue.isNotNull(hrOnClickListener)) {
                orgCompositeTopWidget.getOrgHrLL().setOnClickListener(hrOnClickListener);
            }
            if (AssertValue.isNotNull(myselfOnClickListener)) {
                orgCompositeTopWidget.getMyselfLL().setOnClickListener(myselfOnClickListener);
            }
            orgCompositeTopWidget.setTag(orgCompositeUserListBean);
            return orgCompositeTopWidget;
        } else {
            JupiterRowStyleSutitleLayout tempView = new JupiterRowStyleSutitleLayout(mContext);

            if (this.selectMode){
                tempView.setSelectMode(true);
                tempView.setOnSelectListener(new OnSelectListener() {
                    @Override
                    public void onSelect(View view, boolean mode) {
                        OrgCompositeUserListBean tempOrgCompositeUserListBean = (OrgCompositeUserListBean) view.getTag();
                        tempOrgCompositeUserListBean.setSelected(mode);
                    }
                });
                tempView.select(orgCompositeUserListBean.isSelected());
            }

            tempView.getTitleTV().setText(orgCompositeUserListBean.getUser().getName());
            tempView.getSutitleTv().setText(orgCompositeUserListBean.getUser().getNo() + " " + orgCompositeUserListBean.getUser().getName());
            tempView.getTimeTv().setVisibility(View.GONE);
            tempView.getArrowRightIV().setVisibility(View.GONE);
            tempView.setTag(orgCompositeUserListBean);

            return tempView;
        }
    }
}
