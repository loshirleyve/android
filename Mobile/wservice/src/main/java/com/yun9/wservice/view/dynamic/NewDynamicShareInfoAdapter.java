package com.yun9.wservice.view.dynamic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;

import java.util.List;

/**
 * Created by Leon on 15/6/11.
 */
public class NewDynamicShareInfoAdapter extends JupiterAdapter {

    private Context mContext;

    private List<OrgAndUserBean> mOrgAndUserBeans;

    public NewDynamicShareInfoAdapter(Context context, List<OrgAndUserBean> orgAndUserBeans) {
        this.mContext = context;
        this.mOrgAndUserBeans = orgAndUserBeans;
    }

    @Override
    public int getCount() {
        return mOrgAndUserBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mOrgAndUserBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewDynamicShareToItemLayout newDynamicShareToItemLayout = null;

        OrgAndUserBean orgAndUserBean = mOrgAndUserBeans.get(position);

        if (AssertValue.isNotNull(convertView)) {
            newDynamicShareToItemLayout = (NewDynamicShareToItemLayout) convertView;
        } else {
            newDynamicShareToItemLayout = new NewDynamicShareToItemLayout(mContext);
        }

        newDynamicShareToItemLayout.setTag(orgAndUserBean);

        if (OrgAndUserBean.TYPE_ORG.equals(orgAndUserBean.getType())) {
            newDynamicShareToItemLayout.getTextView().setText("&"+orgAndUserBean.getOrg().getName());
        }

        if (OrgAndUserBean.TYPE_USER.equals(orgAndUserBean.getType())) {
            newDynamicShareToItemLayout.getTextView().setText("@"+orgAndUserBean.getUser().getName());
        }

        return newDynamicShareToItemLayout;
    }
}
