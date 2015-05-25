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
public class DynamicSessionAdapter  extends JupiterAdapter{

    private List<MsgSession> msgSessionList;

    private Context     mContext;


    public DynamicSessionAdapter(Context context ,List<MsgSession> msgSessionList){
        this.msgSessionList = msgSessionList;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(this.msgSessionList)){
            return this.msgSessionList.size();
        }else {
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

        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.fragment_dynamic_session_item, null);
        }

        JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView
                .findViewById(R.id.msg_card_session_rc);

        jupiterRowStyleSutitleLayout.setTag(msgSession);
        jupiterRowStyleSutitleLayout.getTitleTV().setText(msgSession.getFromusername());
        convertView.setTag(msgSession);
        return convertView;
    }

}
