package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardMain;

import java.util.ArrayList;
import java.util.List;

public class MsgCardListActivity extends JupiterFragmentActivity {

    public static final String ARG_TYPE = "type";

    public static final String ARG_VALUE = "value";

    private String mType;

    private String mValue;

    private Logger logger = Logger.getLogger(MsgCardListActivity.class);

    @ViewInject(id=R.id.msg_card_list_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id=R.id.msg_card_lv)
    private ListView msgCardList;

    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MsgCardListActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取传递的参数
        if (AssertValue.isNotNull(this.getIntent().getExtras())) {
            mType = this.getIntent().getExtras().getString(ARG_TYPE);
            mValue = this.getIntent().getExtras().getString(ARG_VALUE);
        }

        titleBar.getTitleLeft().setVisibility(View.VISIBLE);
        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCardListActivity.this.finish();
            }
        });


        MsgCardListAdapter msgCardListAdapter = new MsgCardListAdapter(this,this.initMsgCard());
        msgCardList.setAdapter(msgCardListAdapter);
        msgCardList.setOnItemClickListener(msgCardOnItemClickListener);

   }


    private AdapterView.OnItemClickListener msgCardOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MsgCard msgCard = (MsgCard) view.getTag();

            logger.d("消息卡片点击！"+msgCard.getMain().getFrom());

            Bundle bundle = new Bundle();
            bundle.putSerializable(MsgCardDetailActivity.ARG_MSG_CARD,msgCard);
            MsgCardDetailActivity.start(MsgCardListActivity.this,bundle);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_msg_card_list;
    }

    private List<MsgCard> initMsgCard(){
        List<MsgCard> msgCards = new ArrayList<MsgCard>();

        for(int i=0;i<10;i++){
            msgCards.add(this.createMsgCard(i));
        }

        return msgCards;
    }

    private MsgCard createMsgCard(int i){
        MsgCard msgCard = new MsgCard();

        MsgCardMain msgCardMain = new MsgCardMain();

        msgCardMain.setId(i+"");
        msgCardMain.setContent("测试内容"+i);
        msgCardMain.setFrom("Leon"+i);

        msgCard.setMain(msgCardMain);

        return msgCard;
    }
}
