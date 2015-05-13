package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCardDetailActivity extends JupiterFragmentActivity{

    public final static  String ARG_MSG_CARD = "MSG_CARD";

    private MsgCard msgCard;

    @ViewInject(id = R.id.msg_card_detail_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id= R.id.msg_card_detail)
    private MsgCardInDetailWidget msgCardInDetailWidget;



    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MsgCardDetailActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AssertValue.isNotNull(this.getIntent().getExtras())){
            msgCard = (MsgCard) this.getIntent().getExtras().getSerializable(ARG_MSG_CARD);
        }

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置消息卡片信息

        //设置用户评论、转发、点赞
        LayoutInflater lf = this.getLayoutInflater().from(this);

        View commentView =  lf.inflate(R.layout.widget_msg_card_in_detail_comments, null);
        View praiseView = lf.inflate(R.layout.widget_msg_card_in_detail_praise, null);
        View shareView = lf.inflate(R.layout.widget_msg_card_in_detail_share,null);
        List<View> tabViews = new ArrayList<>();
        tabViews.add(commentView);
        tabViews.add(praiseView);
        tabViews.add(shareView);
        MsgCardDetailViewPagerAdapter msgCardDetailViewPagerAdapter= new MsgCardDetailViewPagerAdapter(tabViews);
        this.msgCardInDetailWidget.getViewPager().setAdapter(msgCardDetailViewPagerAdapter);
        this.msgCardInDetailWidget.getViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MsgCardDetailActivity.this.msgCardInDetailWidget.getSegmentedGroup().selectItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.msgCardInDetailWidget.getSegmentedGroup().setOnTabClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MsgCardDetailActivity.this.msgCardInDetailWidget.getViewPager().setCurrentItem();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_detail;
    }

}
