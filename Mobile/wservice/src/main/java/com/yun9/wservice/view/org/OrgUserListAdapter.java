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
public class OrgUserListAdapter extends BaseAdapter {

    private List<OrgUserBean> mOrgUserBeans;

    private Context mContext;

    private View.OnClickListener hrOnClickListener;

    private View.OnClickListener groupOnClickListener;

    private View.OnClickListener myselfOnClickListener;

    public OrgUserListAdapter(Context context, List<OrgUserBean> orgUserBeans) {
        this.mOrgUserBeans = orgUserBeans;
        this.mContext = context;
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
        if (AssertValue.isNotNullAndNotEmpty(mOrgUserBeans)) {
            return mOrgUserBeans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mOrgUserBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //由于会根据不同的类型决定显示不同的view,所以不能重用convertView,必须每次创建新的view

        OrgUserBean orgUserBean = mOrgUserBeans.get(position);
        if (orgUserBean.isTop()) {
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
            orgCompositeTopWidget.setTag(orgUserBean);
            return orgCompositeTopWidget;
        } else {
            JupiterRowStyleSutitleLayout tempView = new JupiterRowStyleSutitleLayout(mContext);
            String title = orgUserBean.getName();
            if (orgUserBean.isSelectMode()){
                title = title+" 选择模式";

                tempView.setSelectMode(true);
                tempView.setOnSelectListener(new OnSelectListener() {
                    @Override
                    public void onSelect(View view, boolean mode) {
                        OrgUserBean tempOrgUserBean = (OrgUserBean) view.getTag();
                        tempOrgUserBean.setSelected(mode);
                    }
                });
            }
            tempView.getTitleTV().setText(title);

            tempView.getSutitleTv().setText(orgUserBean.getNo() + " " + orgUserBean.getName());
            tempView.getTimeTv().setVisibility(View.GONE);
            tempView.getArrowRightIV().setVisibility(View.GONE);
            tempView.setTag(orgUserBean);

            return tempView;
        }
    }
}
