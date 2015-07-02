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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/1.
 */
public class OrgPhoneUserAdapter extends JupiterAdapter {

    private Context mContext;

    private List<PhoneUser> users;

    private boolean selectMode=true;

    public OrgPhoneUserAdapter(Context context, List<PhoneUser> users) {
        this.mContext = context;
        this.users = users;//bulied();
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(users)) {
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


    public List<PhoneUser> bulied()
    {
        users=new ArrayList<>();
        PhoneUser user=new PhoneUser();
        user.setUsername("阮小玉");
        user.setUsernumber("13697110552");

        PhoneUser user1=new PhoneUser();
        user1.setUsername("权志龙");
        user1.setUsernumber("1369101459");

        PhoneUser user2=new PhoneUser();
        user2.setUsername("崔胜贤");
        user2.setUsernumber("136984939204");

        PhoneUser user3=new PhoneUser();
        user3.setUsername("李胜利");
        user3.setUsernumber("13593796895");

        PhoneUser user4=new PhoneUser();
        user4.setUsername("东永裴");
        user4.setUsernumber("1369998778");

        PhoneUser user5=new PhoneUser();
        user5.setUsername("姜大声");
        user5.setUsernumber("135577960");

        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        return users;

    }


}
