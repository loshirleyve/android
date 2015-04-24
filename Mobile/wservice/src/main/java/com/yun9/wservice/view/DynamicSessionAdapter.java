package com.yun9.wservice.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;

import java.util.List;

/**
 * Created by Leon on 15/4/21.
 */
public class DynamicSessionAdapter  extends JupiterAdapter{

    private List<MsgCard> msgCardList;
    private Context mContext;


    public DynamicSessionAdapter(Context context ,List<MsgCard> msgCardList){
        this.msgCardList = msgCardList;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(this.msgCardList)){
            return this.msgCardList.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return msgCardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgCard msgCard = this.msgCardList.get(position);

        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.fragment_dynamic_session_item, null);
        }

        JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout) convertView
                .findViewById(R.id.msg_card_session_rc);

        jupiterRowStyleSutitleLayout.setTag(msgCard);
        jupiterRowStyleSutitleLayout.getTitleTV().setText(msgCard.getMain().getFrom());
        convertView.setTag(msgCard);

//        holder.msgCardView.load(msgCard);
//        holder.msgCardOtherView.load(msgCard);
        return convertView;
    }

}
