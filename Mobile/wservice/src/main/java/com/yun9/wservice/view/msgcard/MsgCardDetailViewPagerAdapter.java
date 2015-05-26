package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun9.jupiter.view.JupiterPagerAdapter;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/4.
 */
public class MsgCardDetailViewPagerAdapter extends JupiterPagerAdapter {

    private List<View> mListViews;

    public MsgCardDetailViewPagerAdapter(Context ctx,MsgCard msgCard){
        // 评论，点赞，分享
        MsgCardCommentListWidget commentView =  new MsgCardCommentListWidget(ctx);
        commentView.buildWithData(msgCard);
        View praiseView = LayoutInflater.from(ctx).inflate(R.layout.widget_msg_card_in_detail_praise, null);
        View shareView = LayoutInflater.from(ctx).inflate(R.layout.widget_msg_card_in_detail_share, null);
        mListViews = new ArrayList<View>();
        mListViews.add(commentView);
        mListViews.add(praiseView);
        mListViews.add(shareView);
    }

    @Override
    public int getCount() {
        if (mListViews !=null){
            return mListViews.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;//官方提示这样写
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

    @Override
    public int getDipHeight(int position) {
        return 500;
    }
}
