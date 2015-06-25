package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.widget.JupiterSegmentedGroup;
import com.yun9.jupiter.widget.JupiterSegmentedGroupAdapter;
import com.yun9.jupiter.widget.JupiterSegmentedItem;
import com.yun9.jupiter.widget.JupiterSegmentedItemModel;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/5/14.
 */
public class MsgCardDetailSegmentedItemAdapter implements JupiterSegmentedGroupAdapter {

    private List<JupiterSegmentedItemModel> itemList;

    public MsgCardDetailSegmentedItemAdapter(Context context) {
        itemList = new ArrayList<>();
        JupiterSegmentedItemModel model =
                new JupiterSegmentedItemModel(R.string.msg_card_comment,R.drawable.com111,R.drawable.com222);

        model.setDesc(20);
        itemList.add(model);
        model =
                new JupiterSegmentedItemModel(R.string.msg_card_praise,R.drawable.star1,R.drawable.star2);
        model.setDesc(17);
        itemList.add(model);
        model =
                new JupiterSegmentedItemModel(R.string.msg_card_share,R.drawable.fw1,R.drawable.fw2);
        model.setDesc(1000);
        itemList.add(model);

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public JupiterSegmentedItemModel getTabInfo(int position) {
        return itemList.get(position);
    }
}
