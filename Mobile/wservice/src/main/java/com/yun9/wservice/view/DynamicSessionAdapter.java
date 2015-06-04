package com.yun9.wservice.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgSession;

import java.util.List;

/**
 * Created by Leon on 15/4/21.
 */
public class DynamicSessionAdapter extends JupiterAdapter {

    private List<MsgSession> msgSessionList;

    private Context mContext;


    public DynamicSessionAdapter(Context context, List<MsgSession> msgSessionList) {
        this.msgSessionList = msgSessionList;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(this.msgSessionList)) {
            return this.msgSessionList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return msgSessionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgSession msgSession = this.msgSessionList.get(position);
        JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = null;

        if (convertView == null) {
            jupiterRowStyleSutitleLayout = new JupiterRowStyleSutitleLayout(mContext);
        }else{
            jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
        }

        jupiterRowStyleSutitleLayout.setTag(msgSession);
        jupiterRowStyleSutitleLayout.getTitleTV().setText(msgSession.getFromusername());

        return jupiterRowStyleSutitleLayout;
    }

}
