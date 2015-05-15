package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.widget.JupiterSegmentedGroup;
import com.yun9.jupiter.widget.JupiterSegmentedGroupAdapter;
import com.yun9.jupiter.widget.JupiterSegmentedItem;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/5/14.
 */
public class MsgCardDetailSegmentedItemAdapter implements JupiterSegmentedGroupAdapter {

    private List<JupiterSegmentedItem> itemList;

    public MsgCardDetailSegmentedItemAdapter(Context context) {
        itemList = new ArrayList<>();
        ViewGroup itemWrapperView = (ViewGroup) LayoutInflater.from(context)
                                                .inflate(R.layout.segmented_item_wrapper, null);
        JupiterSegmentedItem item = (JupiterSegmentedItem) itemWrapperView.findViewById(R.id.segmented_item);
        itemWrapperView.removeView(item);
        item.getTitleTextTV().setText(R.string.msg_card_comment);
        item.setIcoImage(R.drawable.com1);
        item.setIcoImageSelected(R.drawable.com2);
        itemList.add(item);
        itemWrapperView = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.segmented_item_wrapper, null);
        item = (JupiterSegmentedItem) itemWrapperView.findViewById(R.id.segmented_item);
        itemWrapperView.removeView(item);
        item.getTitleTextTV().setText(R.string.msg_card_praise);
        item.setIcoImage(R.drawable.star1);
        item.setIcoImageSelected(R.drawable.star2);
        itemList.add(item);
        itemWrapperView = (ViewGroup) LayoutInflater.from(context)
                .inflate(R.layout.segmented_item_wrapper, null);
        item = (JupiterSegmentedItem) itemWrapperView.findViewById(R.id.segmented_item);
        itemWrapperView.removeView(item);
        item.getTitleTextTV().setText(R.string.msg_card_share);
        item.setIcoImage(R.drawable.fw1);
        item.setIcoImageSelected(R.drawable.fw2);
        itemList.add(item);


    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public JupiterSegmentedItem getTab(int position) {
        return itemList.get(position);
    }

    @Override
    public View getView(int position) {
        return null;
    }
}
