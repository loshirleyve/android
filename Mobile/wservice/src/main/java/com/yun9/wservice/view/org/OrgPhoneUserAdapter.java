package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Context;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


    public OrgPhoneUserAdapter(Context context, List<PhoneUser> users) {
        this.mContext = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(users)) {
            return users.size();
        } else {
            return 0;
        }
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
            item.setShowArrowButton(true);
            item.getMainIV().setImageResource(R.drawable.user_group);
            item.getArrowRightButton().setOnClickListener(addueronclick);
        } else {
            item = (JupiterRowStyleSutitleLayout) convertView;
        }
        item.getTitleTV().setText(user.getUsername());
        item.getSutitleTv().setText(user.getUsernumber());
        item.select(user.isSelected());
        item.setTag(user);
        return item;
    }


    View.OnClickListener addueronclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str_num ="13697110552";//得到电话号码
            String str_content ="权志龙，李胜贤，崔胜贤，东永裴，姜大声";//得到短信内容
            SmsManager smsManager = SmsManager.getDefault();//得到短信管理器
            //由于短信可能较长，故将短信拆分
            ArrayList<String> texts = smsManager.divideMessage(str_content);
            for(String text : texts){
                smsManager.sendTextMessage(str_num, null, text, null, null);//分别发送每一条短信
            }
            Toast.makeText(mContext, "发送成功!", Toast.LENGTH_LONG).show();//提示成功

        }
    };

}
