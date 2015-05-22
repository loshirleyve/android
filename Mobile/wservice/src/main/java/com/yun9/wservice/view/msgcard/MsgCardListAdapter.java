package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

import java.util.List;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardListAdapter extends JupiterAdapter {

    private List<MsgCard> msgCardList;

    private Context mContext;

    private static final Logger logger = Logger.getLogger(MsgCardListAdapter.class);

    public MsgCardListAdapter(Context context,List<MsgCard> msgCardList){
        this.msgCardList = msgCardList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNullAndNotEmpty(this.msgCardList)){
            return this.msgCardList.size();
        }else{
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

        convertView = View.inflate(mContext,
                    R.layout.activity_msg_card_list_item, null);

        MsgCardWidget msgCardWidget = (MsgCardWidget) convertView
                .findViewById(R.id.msg_card_list_item);

        msgCard.setOnPraiseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点赞！");
            }
        });
        msgCard.setOnForwardClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("转发！");
            }
        });
        msgCard.setOnCommentClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("评论！");
            }
        });
        msgCard.setOnActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("动作！");
            }
        });

        msgCardWidget.setTag(msgCard);
        msgCardWidget.buildWithData(msgCard);

        convertView.setTag(msgCard);
        return convertView;
    }
}
