package com.yun9.wservice.view.org;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;

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
            tempView.getTimeTv().setVisibility(View.GONE);
            tempView.getArrowRightIV().setVisibility(View.GONE);
            tempView.setMainContentGravity(Gravity.CENTER);
            convertView = tempView;
        }
        tempView.getMainIV().setImageResource(R.drawable.user_head);
        CacheUser cacheUser = UserCache.getInstance().getUser(orgCompositeUserListBean.getUser().getId());
        if (AssertValue.isNotNull(cacheUser) && AssertValue.isNotNullAndNotEmpty(cacheUser.getUrl())) {
            ImageLoaderUtil.getInstance(mContext).displayImage(cacheUser.getUrl(), tempView.getMainIV());
        }
        tempView.getTitleTV().setText(orgCompositeUserListBean.getUser().getName());
        if (AssertValue.isNotNullAndNotEmpty(orgCompositeUserListBean.getUser().getSignature())) {
            tempView.getSutitleTv().setText(orgCompositeUserListBean.getUser().getSignature());
        } else {
            tempView.getSutitleTv().setVisibility(View.GONE);
        }
        tempView.setTag(orgCompositeUserListBean);
        if (this.selectMode) {
            tempView.setSelectMode(true);
            tempView.setOnSelectListener(onSelectListener);
            tempView.select(orgCompositeUserListBean.isSelected());
        } else {
            tempView.setSelectMode(false);
            tempView.setOnClickListener(onClickForDetail);
        }

        return convertView;
    }
}
