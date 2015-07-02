package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.PhoneUser;

import java.util.List;

/**
 * Created by Leon on 15/6/1.
 */
public class OrgPhoneUserAdapter extends JupiterAdapter {

    private Context mContext;

    private List<PhoneUser> users;

    private boolean selectMode;

    public OrgPhoneUserAdapter(Context context, List<PhoneUser> orgListBeans) {
        this.mContext = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNull(users)) {
            return users.size();
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
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhoneUser user = users.get(position);
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
        item.getTitleTV().setText(user.getUsername());
        item.getSutitleTv().setText(user.getUsernumber());
        item.select(user.isSelected());
        item.setTag(user);
        return item;
    }



}
